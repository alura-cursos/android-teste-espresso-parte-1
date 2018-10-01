package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ListaLeilaoTelaTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity =
            new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);

    @Test
    public void deve_AparecerUmLeilao_QuandoCarregarUmLeilaoNaApi() throws IOException {
        TesteWebClient webClient = new TesteWebClient();

        boolean bancoDeDadosNaoFoiLimpo = !webClient.limpaBancoDeDados();
        if(bancoDeDadosNaoFoiLimpo){
            Assert.fail("Banco de dados não foi limpo");
        }

        Leilao carroSalvo = webClient.salva(new Leilao("Carro"));
        if(carroSalvo == null){
            Assert.fail("Leilão não foi salvo");
        }

        activity.launchActivity(new Intent());

        onView(withText("Carro"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void deve_AparecerDoisLeiloes_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        TesteWebClient webClient = new TesteWebClient();

        boolean bancoDeDadosNaoFoiLimpo = !webClient.limpaBancoDeDados();
        if(bancoDeDadosNaoFoiLimpo){
            Assert.fail("Banco de dados não foi limpo");
        }

        Leilao carroSalvo = webClient.salva(new Leilao("Carro"));
        Leilao computadorSalvo = webClient.salva(new Leilao("Computador"));
        if(carroSalvo == null || computadorSalvo == null){
            Assert.fail("Leilão não foi salvo");
        }

        activity.launchActivity(new Intent());

        onView(withText("Carro"))
                .check(matches(isDisplayed()));

        onView(withText("Computador"))
                .check(matches(isDisplayed()));
    }

}