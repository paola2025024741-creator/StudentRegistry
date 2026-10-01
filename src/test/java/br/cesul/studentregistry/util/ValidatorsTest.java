package br.cesul.studentregistry.util;

//como eu penso nessa classe na metodologia tdd
//  oque a vm vai precisar decidir? ela terá que validar entradas.
//      -nome nn pode ser vazio
//      -email tem q ter formato padrao
//      -nota tem q ser entre 0 e 10

//como testar funcoes c diversas possibilidades?(com diversas possibilidades...)
//      para cada função devemos listar varios exemplos de entradas e saidas esperadas.
//      ao inves de escrever 10 testes iguais mudando so os valores do arrange, utilizamos um teste parametrizado,
//      ou seja, um teste q roxa varias vezes
//      mas cada um com um conjunto de dados diferente


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorsTest {

    //notBlank -> sera true se nn é null e tem algo alem de espaços

    //indica q esse metodod vai ser executado varias vezes
    //uma pra cada parametro q vc colocar nele
    @ParameterizedTest
    @DisplayName("notBlank deve detectar strings validas/invalidas")
    @CsvSource(
            value = {
                    //formato de cada linha: "entrada, resultadoEsperado"
                    //casos validos (espera = true)
                    "'abc', true" , //exemplo de texto normal
                    "' x', true" , //tem espaços mas tb conteudo

                    //casos invalidos: (esperado = false)
                    "' ', false", //so espaços
                    "'', false", //vazio
                    "null, false", //null real
            }, //transforma a palavra null do CSV em valor null de verdade
            nullValues = {"null"}
    )

    void notBlank_tests(String input, boolean expected) {
        boolean result = Validators.notBlank(input);
        assertEquals(expected, result, () -> "notBlank('" + input + "')" + "deveria ser" + expected);
    }
}
