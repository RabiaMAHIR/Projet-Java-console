package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeApp {
    private static Connection connect() {
        String url = "jdbc:sqlite:qr_codes.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public static void createDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS qr_codes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "data TEXT NOT NULL,"
                + "qr_image BLOB NOT NULL,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveQRCodeToDatabase(String data, byte[] qrImage) {
        String sql = "INSERT INTO qr_codes(data, qr_image) VALUES(?,?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data);
            pstmt.setBytes(2, qrImage);
            pstmt.executeUpdate();

            System.out.println("\033[0;34m[1]Le code QR a été enregistré avec succès dans la base de données!\033[0m");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du code QR dans la base de données: " + e.getMessage());
        }
    }



    public static byte[] generateQRCode(String text, int width, int height) throws WriterException {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hintMap);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < byteMatrix.getWidth(); i++) {
            for (int j = 0; j < byteMatrix.getHeight(); j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static void getAllQRCodes() {
        String sql = "SELECT * FROM qr_codes";
        try (Connection conn = connect(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " Data: " + rs.getString("data"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteQRCodeFromDatabase(String data) {
        String sql = "DELETE FROM qr_codes WHERE data = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\033[0;34m[1]Le code QR a été supprimé avec succès de la base de données!\033[0m");
            } else {
                System.out.println("\033[0;31m[1]Code QR non trouvé dans la base de données..\033[0m");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du code QR: " + e.getMessage());
        }
    }


    public static void deleteQRCode(int id) {
        String sql = "DELETE FROM qr_codes WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Code QR avec ID " + id + " supprimé avec succès de la base de données!");
            } else {
                System.out.println("Code QR avec ID " + id + "non trouvé dans la base de données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du code QR: " + e.getMessage());
        }
    }
}

