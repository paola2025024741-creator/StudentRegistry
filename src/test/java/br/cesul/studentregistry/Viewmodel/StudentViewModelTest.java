package br.cesul.studentregistry.Viewmodel;
import br.cesul.studentregistry.config.MongoConfig;
import br.cesul.studentregistry.model.Student;
import br.cesul.studentregistry.repository.StudentRepository;
import br.cesul.studentregistry.viewmodel.StudentListViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// a view model mantem o estado da tela (itens da lista, o loading e o erro)
//e expoe metodos de ação (loadAll, addNew, toggleFlag e updateField


//quais contratos a vm deve cumprir
// loadAll:
//      ligar o loading durante a operacao e desligar no final
//      preencher a lista de itens com oque veio do repository
// addNew:
//      pegar e validar os campos. se invalido nao salva e escreve no label error, se validos, salva no repo e atualiza a lista
//toggleFlag:
//      solicitar ao repository q alternet o active do estudante x e att a lista de itens
//updateField:
// valida a nota, se invalida, nao atualiza e escreve no label error, se valida atualizar no repo e reflete na lista da table

public class StudentViewModelTest {

    private StudentRepository repo;
    private StudentListViewModel vm;

    @BeforeEach
    void setUp() {
        repo = new StudentRepository(MongoConfig.database(), "students_vm_test");
        repo.deleteAll();
        vm = new StudentListViewModel(repo);


    }
    @Test
    @DisplayName("loadall preenche itens e desliga loading no fim")
    void loadAll_changesLoadingAndItems() {

        // Padrão AAA para testes:
        // Arrange (preparar) - cria dados e contexto pra ação
        // Act (agir) - chamamos um comportamento especifico do objeto testado
        // Assert (Afirmar) - Compara o resultado obtido com o resultado esperado

        //Arrange
        repo.save(new Student("Ana", "ana@gmail.com", 7.5, true));
        repo.save(new Student("joao", "joao@gmail.com", 8, true));

        //Act
        vm.loadAll();
       //a vm deve ligar o loadling, consultar o repo.findAll(), e setar itens na lista


        //Assert
       assertFalse(vm.loadingProperty().get(), "loading deve ser falso no final");
       assertEquals(2, vm.getItems().size(), "itens deve conter exatamente 2 alunos");
    }

}
