package it.logicainformatica.projectcrm.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import it.logicainformatica.projectcrm.bean.ProjectCrmBean;
import it.logicainformatica.projectcrm.db.ProjectCrmDB;

public class ProjectCrmRest {
	
	ProjectCrmDB p = new ProjectCrmDB();

	@GetMapping("/stampaDati")
	public List<ProjectCrmBean> stampaDati() {
		List<ProjectCrmBean> lista = p.stampaDati();
		return lista;
	}
}
