package br.com.alura.leilao.api.retrofit.client;

import java.io.IOException;
import java.util.List;

import br.com.alura.leilao.api.retrofit.RetrofitInicializador;
import br.com.alura.leilao.api.retrofit.service.LeilaoService;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeilaoWebClient {

    private final LeilaoService service;

    public LeilaoWebClient() {
        service = new RetrofitInicializador().getLeilaoService();
    }

    public void propoe(Lance lance, Long id, final RespostaListener<Void> listener) {
        Call<Void> call = service.propoe(id, lance);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.sucesso(null);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.falha(t.getMessage());
            }
        });
    }

    public void todos(final RespostaListener<List<Leilao>> listener) {
        Call<List<Leilao>> call = service.todos();
        call.enqueue(new Callback<List<Leilao>>() {
            @Override
            public void onResponse(Call<List<Leilao>> call, Response<List<Leilao>> response) {
                if (temDados(response)) {
                    listener.sucesso(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Leilao>> call, Throwable t) {
                listener.falha(t.getMessage());
            }
        });
    }

    private <T> boolean temDados(Response<T> response) {
        return response.isSuccessful() && response.body() != null;
    }

    public Leilao salva(Leilao leilao) throws IOException {
        Call<Leilao> call = service.salva(leilao);
        Response<Leilao> resposta = call.execute();
        if(temDados(resposta)){
            return resposta.body();
        }
        return null;
    }

    public boolean limpaBancoDeDados() throws IOException {
        Call<Void> call = service.limpaBancoDeDados();
        Response<Void> resposta = call.execute();
        return resposta.isSuccessful();
    }
}
