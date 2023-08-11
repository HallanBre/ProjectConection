package com.br.projetoconexao.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.projetoconexao.Model.Aluno;
import com.br.projetoconexao.Model.Papel;
import com.br.projetoconexao.Repository.AlunoRepository;
import com.br.projetoconexao.Repository.PapelRepository;

@Controller
public class AlunoController {

    @Autowired
    AlunoRepository repository;

    @Autowired
    PapelRepository pRepository;

    //isso esta relacionado ao assunto da ultima aula do jayme, isso serve para "entrelaçar" bancos de dados entre si

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home");
        ArrayList<Papel> papeis = new ArrayList<>();
        papeis = (ArrayList<Papel>) pRepository.findAll();
        mv.addObject("papeis", papeis);
        return mv;
    }

    @PostMapping("/home")
    public String salvar(Aluno aluno) {
        repository.save(aluno);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public ModelAndView lista() {
        ModelAndView mv = new ModelAndView("list");
        ArrayList<Aluno> alunos = new ArrayList<>();
        alunos = (ArrayList<Aluno>) repository.findAll();
        mv.addObject("alunos", alunos);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") int id) {
        repository.deleteById(id);
        return "redirect:/list";

    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("home");
        Aluno aluno = new Aluno();
        aluno = repository.findById(id).get();
        mv.addObject("aluno", aluno);

        return mv;

    }

    @PostMapping("/home")
    public String salvar (Aluno aluno, @RequestParam("pps") List<Integer> papelId){
        ArrayList<Papel> papeis = new ArrayList<>();
        for (Integer id : papelId) {
            papeis.add(pRepository.findById(id).get());
        }
        aluno.setPapeis(papeis);
        repository.save(aluno);
        return "redirect:/list";
    }

}
