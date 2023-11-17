package com.auladevmobile.jogoprapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JogarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JogarFragment extends Fragment {

    Button mBotaoCadastrarPR;
    Button mBotaoPular;
    Button mBotaoExibirResp;
    TextView mTextPergunta;
    TextView mTextResposta;
    List<Questoes> mListQuestoes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JogarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JogarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JogarFragment newInstance(String param1, String param2) {
        JogarFragment fragment = new JogarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jogar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBotaoCadastrarPR = getActivity().findViewById(R.id.botaoCadastrarPR);
        mBotaoPular = getActivity().findViewById(R.id.botaoPular);
        mBotaoExibirResp = getActivity().findViewById(R.id.botaoExibirResposta);
        mTextPergunta = getActivity().findViewById(R.id.pergunta);
        mTextResposta = getActivity().findViewById(R.id.resposta);

        // Recupera todas as questões cadastradas no banco de dados
        mListQuestoes = BancoDeDados.getBancoDeDados(getActivity())
                .meuDAO().pesquisarTodasQuestoes();

        proximaQuestão();

        mBotaoCadastrarPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new CadastrarFragment()).commit();
            }
        });

        mBotaoPular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proximaQuestão();
            }
        });

        mBotaoExibirResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextResposta.setVisibility(View.VISIBLE);
            }
        });


    }

    private void proximaQuestão(){

        if (!mListQuestoes.isEmpty()){
            int totalDeQuestoes = mListQuestoes.size();
            int indexAleatorio = new Random().nextInt(totalDeQuestoes);
            Questoes questoes = mListQuestoes.get(indexAleatorio);
            mTextPergunta.setText(questoes.getPergunta());
            mTextResposta.setText(questoes.getResposta());
            mTextResposta.setVisibility(View.GONE);
        }
    }
}