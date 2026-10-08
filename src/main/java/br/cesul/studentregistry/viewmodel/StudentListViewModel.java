package br.cesul.studentregistry.viewmodel;

import br.cesul.studentregistry.model.Student;
import br.cesul.studentregistry.repository.StudentRepository;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentListViewModel {
    //propriedades observaveis -> estado da tela
    private final ObservableList<Student> items = FXCollections.observableArrayList();

    //indicador de visibilidade do loading
    //a view faz o bind progress.visibleProperty().bind (vm.loadingProperty)
    private final BooleanProperty loading = new SimpleBooleanProperty(false);

    //dependencia de dados
    private final StudentRepository repository;
    public StudentListViewModel(StudentRepository repository) {
        this.repository = repository;
    }


    public BooleanProperty loadingProperty() {
        return loading;
    }

    public ObservableList<Student> getItems() {
        return items;
    }

    //carregar todos os estudantes da repo
    //ligar o loading durante a operacao
    //limpar o lblError
    //setar os itens na lista
    //em caso de falha, setar o lblError
    public void loadAll() {
        loadingProperty().set(true);

    }


}
