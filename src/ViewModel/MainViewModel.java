package ViewModel;

import Model.Mediator.Model;
import ViewModel.StatisticsViewModels.GameStatisticsViewModel;
import ViewModel.StatisticsViewModels.OverallStatisticsViewModel;
import ViewModel.StatisticsViewModels.PersonalStatisticsViewModel;
import ViewModel.StatisticsViewModels.StatisticsPanelViewModel;
import javafx.application.Platform;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class MainViewModel implements LocalListener<Object, Object> {
    private Model model;
    private GameViewModel gameViewModel;
    private LoginViewModel loginViewModel;
    private RulesViewModel rulesViewModel;
    private RegisterViewModel registerViewModel;
    private WaitingViewModel waitingViewModel;
    private CharacterListViewModel characterListViewModel;
    private GamePickerViewModel gamePickerViewModel;
    private AvatarsViewModel avatarsViewModel;
    private GameStatisticsViewModel gameStatisticsViewModel;
    private WinnerViewModel winnerViewModel;
    private OverallStatisticsViewModel overallStatisticsViewModel;
    private PersonalStatisticsViewModel personalStatisticsViewModel;
    private EmbeddedViewModel embeddedViewModel;
    private RightPanelViewModel rightPanelViewModel;
    private LeftPanelViewModel leftPanelViewModel;
    private StatisticsPanelViewModel statisticsPanelViewModel;
    private EditProfileViewModel editProfileViewModel;

    public MainViewModel(Model model) {
        this.model = model;
        this.gameViewModel = new GameViewModel(model);
        this.loginViewModel = new LoginViewModel(model);
        this.rulesViewModel = new RulesViewModel(model);
        this.registerViewModel = new RegisterViewModel(model);
        this.waitingViewModel = new WaitingViewModel(model);
        this.characterListViewModel = new CharacterListViewModel(model);
        this.gamePickerViewModel = new GamePickerViewModel(model);
        this.avatarsViewModel = new AvatarsViewModel(model);
        this.gameStatisticsViewModel = new GameStatisticsViewModel(model);
        this.winnerViewModel = new WinnerViewModel(model);
        this.overallStatisticsViewModel = new OverallStatisticsViewModel(model);
        this.personalStatisticsViewModel = new PersonalStatisticsViewModel(model);
        this.embeddedViewModel = new EmbeddedViewModel(model);
        this.rightPanelViewModel = new RightPanelViewModel(model);
        this.leftPanelViewModel = new LeftPanelViewModel(model);
        this.statisticsPanelViewModel = new StatisticsPanelViewModel(model);
        this.editProfileViewModel = new EditProfileViewModel(model);
        this.model.addListener(this);
    }

    public GameViewModel getGameViewModel() {
        return gameViewModel;
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public RulesViewModel getRulesViewModel() {
        return rulesViewModel;
    }

    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }

    public WaitingViewModel getWaitingViewModel() {
        return waitingViewModel;
    }

    public CharacterListViewModel getCharacterListViewModel() {
        return characterListViewModel;
    }

    public GamePickerViewModel getGamePickerViewModel() {
        return gamePickerViewModel;
    }

    public AvatarsViewModel getAvatarsViewModel() {
        return avatarsViewModel;
    }

    public GameStatisticsViewModel getGameStatisticsViewModel() {
        return gameStatisticsViewModel;
    }

    public WinnerViewModel getWinnerViewModel() {
        return winnerViewModel;
    }

    public OverallStatisticsViewModel getOverallStatisticsViewModel() {
        return overallStatisticsViewModel;
    }

    public PersonalStatisticsViewModel getPersonalStatisticsViewModel() {
        return personalStatisticsViewModel;
    }

    public EmbeddedViewModel getEmbeddedViewModel() {
        return embeddedViewModel;
    }

    public RightPanelViewModel getRightPanelViewModel() {
        return rightPanelViewModel;
    }

    public LeftPanelViewModel getLeftPanelViewModel() {
        return leftPanelViewModel;
    }

    public StatisticsPanelViewModel getStatisticsPanelViewModel() {
        return statisticsPanelViewModel;
    }

    public EditProfileViewModel getEditProfileViewModel() {
        return editProfileViewModel;
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        switch (event.getPropertyName()) {
            case "killedPlayer":
                Platform.runLater(() -> {
                    this.gameViewModel = new GameViewModel(model);
                });
                break;
            case "gameAborted":
        }
    }

    public void windowClosed() {
        model.closeApplication();
    }
}
