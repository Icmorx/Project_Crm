package it.logicainformatica.projectcrm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.logicainformatica.projectcrm.bean.ProjectCrmBean;
import it.logicainformatica.projectcrm.db.ProjectCrmDB;


@RestController
@RequestMapping("/gestione-anagrafica")
public class ProjectCrmRest {
	
	ProjectCrmDB p = new ProjectCrmDB();

	// SERVIZIO CHE SCRIVE
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void inserisciAnagrafica(@RequestBody ProjectCrmBean pB) {
		p.inserisciUtente(pB);
		p.newFile(pB);
	}
	
	// SERVIZIO CHE STAMPA DAL DB
	@GetMapping("/stampaDati")
	public List<ProjectCrmBean> stampaDati() {
		List<ProjectCrmBean> lista = p.stampaDati();
		return lista;
	}
}
