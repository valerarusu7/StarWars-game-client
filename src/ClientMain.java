import Model.Mediator.Model;
import Model.Mediator.ModelManager;
import View.MainView;
import ViewModel.MainViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import utility.observer.subject.PropertyChangeThread;

import java.util.concurrent.TimeUnit;

public class ClientMain extends Application {
    private Model model;

    @Override
    public void start(Stage primaryStage) {
        Model model = new ModelManager();
        this.model = model;

        MainViewModel viewModel = new MainViewModel(model);
        MainView view = new MainView(viewModel);
        view.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        model.closeApplication();
		PropertyChangeThread.closeAll();
		ModelManager.executorService.shutdown();
		ModelManager.executorService.awaitTermination(10, TimeUnit.MILLISECONDS); // wait for 10s in this case
		ModelManager.executorService.shutdownNow();
		Platform.exit();
		System.exit(0);
    }
}
