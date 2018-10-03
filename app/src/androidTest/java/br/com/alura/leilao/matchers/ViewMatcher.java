package br.com.alura.leilao.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ViewMatcher {

    public static Matcher<? super View> apareceLeilaoNaPosicao(final int posicaoEsperada,
                                                               final String descricaoEsperada,
                                                               final double maiorLanceEsperado) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            private Matcher<View> displayed = isDisplayed();
            private final FormatadorDeMoeda formatador = new FormatadorDeMoeda();
            private final String maiorLanceFormatadoEsperado = formatador.formata(maiorLanceEsperado);

            @Override
            public void describeTo(Description description) {
                description.appendText("View com descrição ").appendValue(descricaoEsperada)
                        .appendText(", maior lance ").appendValue(maiorLanceFormatadoEsperado)
                        .appendText(" na posição ").appendValue(posicaoEsperada)
                .appendText(" ");
                description.appendDescriptionOf(displayed);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolderDevolvido = item.findViewHolderForAdapterPosition(posicaoEsperada);
                if(viewHolderDevolvido == null){
                    throw new IndexOutOfBoundsException("View do ViewHolder na posição " + posicaoEsperada +
                            " não foi encontrada");
                }
                View viewDoViewHolder = viewHolderDevolvido.itemView;
                boolean temDescricaoEsperada = verificaDescricaoEsperada(viewDoViewHolder);
                boolean temMaiorLanceEsperado = verificaMaiorLanceEsperado(viewDoViewHolder);
                displayed = isDisplayed();
                return temDescricaoEsperada &&
                        temMaiorLanceEsperado &&
                        displayed.matches(viewDoViewHolder);
            }

            private boolean verificaMaiorLanceEsperado(View viewDoViewHolder) {
                TextView textViewMaiorLance = viewDoViewHolder.findViewById(R.id.item_leilao_maior_lance);
                return textViewMaiorLance.getText().toString()
                        .equals(maiorLanceFormatadoEsperado);
            }

            private boolean verificaDescricaoEsperada(View viewDoViewHolder) {
                TextView textViewDescricao = viewDoViewHolder.findViewById(R.id.item_leilao_descricao);
                return textViewDescricao.getText()
                        .toString().equals(descricaoEsperada);
            }
        };
    }
}
