package tech.devinhouse.labschool.dto;

import lombok.Data;
import java.time.LocalDate;
@Data

public class AlunoResponse {

    private Integer codigo;
    private String nome;
    private String telefone;
    private LocalDate dataNascimento;
    private long cpf;
    private String situacao;
    private Float nota;
    private Integer atendimentosPedagogicos;

}
