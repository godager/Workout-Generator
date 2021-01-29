package utils;

public class URL {
    public static String formattedURL(String path) {
        path = path.replace('%', ' ');
        path = path.replaceAll("20", "");
        String sub = path.substring(15);
        String finalPath = "file:///C://Users//" + sub;
        finalPath = finalPath.replace("out/production/WorkoutGenerator/", "src/");
        return finalPath;
    }
}
