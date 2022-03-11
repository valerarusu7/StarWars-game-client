package ViewModel;

import Model.Mediator.Model;
import Model.Mediator.ModelManager;

public class CharacterListViewModel {
    private Model model;

    public CharacterListViewModel(Model model) {
        this.model = model;
    }

    /**
     * Sets currentCharacter in ModelManager
     *
     * @param characterName characterName passed from CharacterListView
     * @see ModelManager#pickCharacter(String)
     */
    public void setCurrentCharacter(String characterName) {
        model.pickCharacter(characterName);
    }
}
