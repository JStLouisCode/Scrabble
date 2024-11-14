public class Controller {
    private final Game model;

    public Controller() {
        model = new Game();
        View view = new View(model);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

}
