package tech.devinhouse.labschool.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.devinhouse.labschool.exception.RegistroExistenteException;
import tech.devinhouse.labschool.exception.RegistroNaoEncontradoException;
import tech.devinhouse.labschool.model.Aluno;
import tech.devinhouse.labschool.model.SituacaoMatricula;
import tech.devinhouse.labschool.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class AlunoService {

    private AlunoRepository repo;

    public Aluno criar (Aluno aluno){
        boolean existe = repo.existsByCpf(aluno.getCpf());
        if (existe){
            throw new RegistroExistenteException("Aluno",aluno.getCpf());
        }
        aluno.setAtendimentosPedagogicos(0);
        aluno = repo.save(aluno);
        return aluno;
    }

    public Aluno atualizar(Aluno aluno) {
        Optional<Aluno> alunoOpt = repo.findById(aluno.getCodigo());
        if (alunoOpt.isEmpty())
            throw new RegistroNaoEncontradoException("Aluno", aluno.getCodigo());
        Aluno alunoBD = alunoOpt.get();
        alunoBD.setSituacao(aluno.getSituacao());
        repo.save(alunoBD);
        return alunoBD;
    }
    public List<Aluno> listarAlunos(){
        return repo.findAll();
    }

    public List<Aluno> listarAlunosPorSituacao (String situacao){
        SituacaoMatricula situacaomatricula = SituacaoMatricula.valueOf(situacao.toUpperCase());
        List<Aluno> alunoOpt = repo.findBySituacao(situacaomatricula);
        if (alunoOpt.isEmpty())
            throw new RegistroNaoEncontradoException("Aluno", situacao);
        return repo.findBySituacao(situacaomatricula);
    }
    public Aluno listarAlunoPorId(Integer codigo) {
        Optional<Aluno> alunopOpt = Optional.ofNullable(repo.findByCodigo(codigo));
        if (alunopOpt.isEmpty())
            throw new RegistroNaoEncontradoException("Aluno", codigo);
        return repo.findByCodigo(codigo);
    }
    public void deletarAluno(Integer codigo) {
        if (!repo.existsById(codigo))
            throw new RegistroNaoEncontradoException("Aluno",codigo);
        repo.deleteById(codigo);
    }
}
