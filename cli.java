
// Added this class for cli implementation
public class cli {
    public static final int CLI_WIDTH = 100;

    public static void printCenteredOptions(String title, String options) {
        int padding = (CLI_WIDTH - options.length()) / 2;
        String formattedText = String.format("%-" + CLI_WIDTH + "s", " ".repeat(padding) + options);

        String border = "=".repeat((CLI_WIDTH - title.length()) / 2);
        String formattedTitle = String.format("%s %s %s", border, title, border);
        System.out.println(formattedTitle);
        System.out.println("|" + formattedText.substring(0, CLI_WIDTH) + "|");
        for (int i = 0; i < CLI_WIDTH + 2; ++i) {
            System.out.print("=");
        }
        System.out.println();
        System.out.print("Enter choice then press enter: ");
    }
}
