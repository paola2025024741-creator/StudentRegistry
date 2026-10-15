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

    @Test
    @DisplayName("addNew valida entradas e insere quando valido")
    void addNew_validatesAndInserts(){

        //vamos considerar 4 cenarios
        //esse teste assegura que mesmo que o repository me trouxesse o dado certo, ele nao seria exibido errado na tela

        //cenario 1- nome inválido
        //aqui nao tem arrange, vai direto pro act
        vm.addNew("  ", "a@x.com", 8);
        assertTrue(vm.getItems().isEmpty(), "nao deve inserir com nome invalido");
        assertTrue(vm.errorProperty().get().contains("Nome"), "a mensagem deve explicar que o nome é invalido");
        //cenario 2- email invalido
        vm.addNew("Ana", "a@com.", 5);
        assertTrue(vm.getItems().isEmpty(), "nao deve inserir com email invalido");
        assertTrue(vm.errorProperty().get().contains("Email"), "a mensagem deve explicar que o nome é invalido");
        //cenario 3- nota invalida
        vm.addNew("Ana", "a@gmail.com", 11);
        assertTrue(vm.getItems().isEmpty(), "nao deve inserir nota invalida");
        assertTrue(vm.errorProperty().get().contains("nota"), "a mensagem deve explicar que a nota é invalida");
        //cenario 4- tudo valido
        vm.addNew("Ana", "ana@gmail.com", 8);
        assertEquals(1,vm.getItems().size(), "deve ter um aluno na lista");
        assertEquals("Ana", vm.getItems().get(0).getName(),"o aluno inserido" + "deve aparecer nos itens da vm");

    }

    @Test
    @DisplayName("toggleFlag alternas 'active' e reflete na lista")
    void toggleFlag_updatesItem(){
        var s = new Student("joao", "joao@gmail.com", 6.0, true);
        repo.save(s);
        vm.loadAll();
        String id = vm.getItems().get(0).getId();

        vm.toggleFlag(id);
        assertFalse(vm.getItems().get(0).isActive(), "deveria ter alternado para falso");
    }

    @Test
    @DisplayName("updateField valida faixa e atualiza nota")
    void updateField_validatesAndUpdates(){
        var s = new Student("joao", "joao@gmail.com", 6.0, true);
        repo.save(s);
        vm.loadAll();
        String id = vm.getItems().get(0).getId();

        //cenario 1- nota fora da faixa
        vm.updateField(id, -1);
        assertTrue(vm.errorProperty().get().contains("nota"),"VM deve exibir erro");

        //cenario 2- valido
        vm.updateField(id,9.0);
        assertEquals(9,vm.getItems().get(0).getGrade(), 0.0001, "a Nota deve ser atualizada e" +
                "refletida na lista");


    }


}
