package br.com.lynx.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.lynx.R;
import br.com.lynx.domain.Cliente;
import br.com.lynx.domain.Questao;

/**
 * Created by Rogerio on 27/07/2017.
 */

public class AvaliacaoPDVNGActivity extends AppCompatActivity {

    private int clienteID;
    private int tipoAvaliacao;
    private Cliente cliente;
    private ArrayList<Questao> perguntas;
    private int perguntaAtiva;
    private TextView txtPergunta;
    private TextView txtPerguntaInfo;
    private Button btnSim;
    private Button btnNao;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    private Button btn0P;
    private Button btn50P;
    private Button btn100P;

    private Button btn25P1;
    private Button btn50P1;
    private Button btn100P1;

    private Button btn20P2;
    private Button btn80P2;
    private Button btn100P2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_pdvng);

        Toolbar toolbar = (Toolbar) findViewById(R.id.avaliacaoPDGNG_Toolbar);
        toolbar.setTitle("Avaliação PDV-NG");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        clienteID = intent.getIntExtra("ClienteID", 0);
        tipoAvaliacao = intent.getIntExtra("TipoAvaliacaoID", 0);

        cliente = new Cliente(this, clienteID);
        cliente.load();

        perguntas = (ArrayList) cliente.getListaPerguntasPDVNG();

        btnSim = (Button) findViewById(R.id.avaliacaoPDVNG_botaoSim);
        btnNao = (Button) findViewById(R.id.avaliacaoPDVNG_botaoNao);
        txtPergunta = (TextView) findViewById(R.id.avaliacaoPDVNG_Pergunta);
        txtPerguntaInfo = (TextView) findViewById(R.id.avaliacaoPDVNG_PerguntaInfoNumero);

        btn0 = (Button) findViewById(R.id.avaliacaoPDVNG_botao0);
        btn1 = (Button) findViewById(R.id.avaliacaoPDVNG_botao1);
        btn2 = (Button) findViewById(R.id.avaliacaoPDVNG_botao2);
        btn3 = (Button) findViewById(R.id.avaliacaoPDVNG_botao3);
        btn4 = (Button) findViewById(R.id.avaliacaoPDVNG_botao4);
        btn5 = (Button) findViewById(R.id.avaliacaoPDVNG_botao5);
        btn6 = (Button) findViewById(R.id.avaliacaoPDVNG_botao6);
        btn7 = (Button) findViewById(R.id.avaliacaoPDVNG_botao7);
        btn8 = (Button) findViewById(R.id.avaliacaoPDVNG_botao8);
        btn9 = (Button) findViewById(R.id.avaliacaoPDVNG_botao9);
        btn10 = (Button) findViewById(R.id.avaliacaoPDVNG_botao10);

        btn0P = (Button) findViewById(R.id.avaliacaoPDVNG_botao0P);
        btn50P = (Button) findViewById(R.id.avaliacaoPDVNG_botao50P);
        btn100P = (Button) findViewById(R.id.avaliacaoPDVNG_botao100P);

        btn25P1 = (Button) findViewById(R.id.avaliacaoPDVNG_botao25P1);
        btn50P1 = (Button) findViewById(R.id.avaliacaoPDVNG_botao50P1);
        btn100P1 = (Button) findViewById(R.id.avaliacaoPDVNG_botao100P1);

        btn20P2 = (Button) findViewById(R.id.avaliacaoPDVNG_botao20P2);
        btn80P2 = (Button) findViewById(R.id.avaliacaoPDVNG_botao80P2);
        btn100P2 = (Button) findViewById(R.id.avaliacaoPDVNG_botao100P1);

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("Sim");
            }
        });
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("Não");
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("9");
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("10");
            }
        });

        btn0P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("0");
            }
        });
        btn50P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("50");
            }
        });
        btn100P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("100");
            }
        });

        btn25P1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("25");
            }
        });
        btn50P1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("50");
            }
        });
        btn100P1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("100");
            }
        });

        btn20P2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("20");
            }
        });
        btn80P2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("80");
            }
        });
        btn100P2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliaResposta("100");
            }
        });

        perguntaAtiva = 1;
        exibePergunta();
    }

    private void avaliaResposta(String resposta) {
        perguntas.get(perguntaAtiva - 1).setResposta(resposta);

        if (perguntaAtiva < perguntas.size()) {
            perguntaAtiva++;
            exibePergunta();
        } else {
            exibeResumo();
        }
    }

    private void setPerguntaSimNao(){
        btnSim.setVisibility(View.VISIBLE);
        btnNao.setVisibility(View.VISIBLE);

        btn0.setVisibility(View.INVISIBLE);
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btn5.setVisibility(View.INVISIBLE);
        btn6.setVisibility(View.INVISIBLE);
        btn7.setVisibility(View.INVISIBLE);
        btn8.setVisibility(View.INVISIBLE);
        btn9.setVisibility(View.INVISIBLE);
        btn10.setVisibility(View.INVISIBLE);

        btn0P.setVisibility(View.INVISIBLE);
        btn50P.setVisibility(View.INVISIBLE);
        btn100P.setVisibility(View.INVISIBLE);
    }

    private void setPerguntaZeroaDez(){
        btnSim.setVisibility(View.INVISIBLE);
        btnNao.setVisibility(View.INVISIBLE);

        btn0.setVisibility(View.VISIBLE);
        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);
        btn5.setVisibility(View.VISIBLE);
        btn6.setVisibility(View.VISIBLE);
        btn7.setVisibility(View.VISIBLE);
        btn8.setVisibility(View.VISIBLE);
        btn9.setVisibility(View.VISIBLE);
        btn10.setVisibility(View.VISIBLE);

        btn0P.setVisibility(View.INVISIBLE);
        btn50P.setVisibility(View.INVISIBLE);
        btn100P.setVisibility(View.INVISIBLE);
    }

    private void setPergunta050100(){
        btnSim.setVisibility(View.INVISIBLE);
        btnNao.setVisibility(View.INVISIBLE);

        btn0.setVisibility(View.INVISIBLE);
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btn5.setVisibility(View.INVISIBLE);
        btn6.setVisibility(View.INVISIBLE);
        btn7.setVisibility(View.INVISIBLE);
        btn8.setVisibility(View.INVISIBLE);
        btn9.setVisibility(View.INVISIBLE);
        btn10.setVisibility(View.INVISIBLE);

        btn0P.setVisibility(View.VISIBLE);
        btn50P.setVisibility(View.VISIBLE);
        btn100P.setVisibility(View.VISIBLE);
    }

    private void setPergunta02550100(){
        btnSim.setVisibility(View.INVISIBLE);
        btnNao.setVisibility(View.INVISIBLE);

        btn0.setVisibility(View.INVISIBLE);
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btn5.setVisibility(View.INVISIBLE);
        btn6.setVisibility(View.INVISIBLE);
        btn7.setVisibility(View.INVISIBLE);
        btn8.setVisibility(View.INVISIBLE);
        btn9.setVisibility(View.INVISIBLE);
        btn10.setVisibility(View.INVISIBLE);

        btn50P.setVisibility(View.INVISIBLE);
        btn100P.setVisibility(View.INVISIBLE);

        btn0P.setVisibility(View.VISIBLE);

        btn25P1.setVisibility(View.VISIBLE);
        btn50P1.setVisibility(View.VISIBLE);
        btn100P1.setVisibility(View.VISIBLE);

        btn20P2.setVisibility(View.INVISIBLE);
        btn80P2.setVisibility(View.INVISIBLE);
        btn100P2.setVisibility(View.INVISIBLE);
    }

    private void setPergunta02080100(){
        btnSim.setVisibility(View.INVISIBLE);
        btnNao.setVisibility(View.INVISIBLE);

        btn0.setVisibility(View.INVISIBLE);
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btn5.setVisibility(View.INVISIBLE);
        btn6.setVisibility(View.INVISIBLE);
        btn7.setVisibility(View.INVISIBLE);
        btn8.setVisibility(View.INVISIBLE);
        btn9.setVisibility(View.INVISIBLE);
        btn10.setVisibility(View.INVISIBLE);

        btn50P.setVisibility(View.INVISIBLE);
        btn100P.setVisibility(View.INVISIBLE);

        btn0P.setVisibility(View.VISIBLE);

        btn25P1.setVisibility(View.VISIBLE);
        btn50P1.setVisibility(View.VISIBLE);
        btn100P1.setVisibility(View.VISIBLE);

        btn0P.setVisibility(View.INVISIBLE);
        btn20P2.setVisibility(View.INVISIBLE);
        btn80P2.setVisibility(View.INVISIBLE);
        btn100P2.setVisibility(View.INVISIBLE);
    }

    private void exibePergunta() {
        txtPerguntaInfo.setText("Pergunta " + String.valueOf(perguntaAtiva) + " de " + perguntas.size());
        txtPergunta.setText(perguntas.get(perguntaAtiva - 1).getPergunta());

        if (perguntas.get(perguntaAtiva - 1).getTipoResposta().equals("Sim/Não")) {
            setPerguntaSimNao();
        } else if (perguntas.get(perguntaAtiva - 1).getTipoResposta().equals("0/50/100")) {
            setPergunta050100();
        } else if (perguntas.get(perguntaAtiva - 1).getTipoResposta().equals("0 a 10")) {
            setPerguntaZeroaDez();
        } else if (perguntas.get(perguntaAtiva - 1).getTipoResposta().equals("0/25/50/100")) {
            setPergunta02550100();
        } else if (perguntas.get(perguntaAtiva - 1).getTipoResposta().equals("0/20/80/100")) {
            setPergunta02080100();
        }

    }

    private void exibeResumo() {
        Intent intent = new Intent(this, AvaliacaoPDVNGResumoActivity.class);
        intent.putParcelableArrayListExtra("Respostas", perguntas);
        intent.putExtra("ClienteID", clienteID);
        intent.putExtra("TipoAvaliacaoID", tipoAvaliacao);
        startActivity(intent);

        finish();
    }
}
