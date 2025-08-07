package Server;

import static spark.Spark.*;
import java.io.*;

public class TriggerServer {

    public static void main(String[] args) {

        // ✅ Set dynamic port for Azure or default to 8080
        port(getAzureAssignedPort());

        // ✅ Serve static files (ExtentReports, uploaded Excel, etc.)
        staticFiles.externalLocation("wwwroot");
        System.out.println("✅ Serving static files from: wwwroot");

        // ✅ Enable CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });

        // ✅ POST: Upload Excel file
        post("/upload-excel", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new javax.servlet.MultipartConfigElement("/temp"));

            try (InputStream is = req.raw().getPart("file").getInputStream()) {
                File uploadDir = new File("wwwroot/uploads");
                if (!uploadDir.exists()) uploadDir.mkdirs();

                File uploadedFile = new File(uploadDir, "Scrappers.xlsx");
                try (OutputStream os = new FileOutputStream(uploadedFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }

                System.out.println("✅ Excel uploaded: " + uploadedFile.getAbsolutePath());
                return "✅ Excel file uploaded successfully. Access it at: /uploads/Scrappers.xlsx";
            } catch (Exception e) {
                res.status(500);
                return "❌ Failed to upload Excel file: " + e.getMessage();
            }
        });

        // ✅ POST: Trigger tests
        post("/run-tests", (req, res) -> {
            try {
                File testProject = new File(".");
                ProcessBuilder pb = new ProcessBuilder("mvn", "test");
                pb.directory(testProject);
                pb.inheritIO();

                Process p = pb.start();
                int exitCode = p.waitFor();

                String message = "✅ Test execution completed.<br><a href='/index1.html' target='_blank'>📄 View Extent Report</a>";
                System.out.println("Returning message: " + message);
                return message;

            } catch (Exception e) {
                res.status(500);
                return "❌ Error: " + e.getMessage();
            }
        });

        // ✅ GET: Health check
        get("/", (req, res) -> "✅ Server is up and running!");
        System.out.println("✅ Spark server started.");
    }

    // ✅ Get dynamic port for Azure or default to 8080
    private static int getAzureAssignedPort() {
        String port = System.getenv("PORT");
        return port != null ? Integer.parseInt(port) : 8080;
    }
}
