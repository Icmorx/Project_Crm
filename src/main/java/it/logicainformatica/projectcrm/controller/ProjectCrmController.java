package it.logicainformatica.projectcrm.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.logicainformatica.projectcrm.bean.ProjectCrmBean;
import it.logicainformatica.projectcrm.db.ProjectCrmDB;

@RestController
@RequestMapping("/project-crm")
public class ProjectCrmController {

	ProjectCrmDB p = new ProjectCrmDB();

	// SERVIZIO CHE SCRIVE
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void inserisciAnagrafica(@RequestBody ProjectCrmBean pB) throws IOException {
		p.inserisciUtente(pB);
		p.scrivoFile(pB);
	}

	// SERVIZIO CHE STAMPA DAL DB
	@GetMapping("/stampaDati")
	public List<ProjectCrmBean> getDati() {
		List<ProjectCrmBean> lista = p.getDati();
		return lista;
	}

	// SERVIZIO CHE LEGGE DAL FILE
	@GetMapping("/leggiDati")
	public ResponseEntity<String> leggiDati() {

		try {
			File file = new File("Project_Crm.txt");

			BufferedReader reader = new BufferedReader(new FileReader(file));

			StringBuilder sB = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sB.append(line);
				sB.append("\n");
			}

			reader.close();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);
			return new ResponseEntity<String>(sB.toString(), headers, HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erroe nel servizio rest leggiDati" + e.getMessage());
		}
		return new ResponseEntity<String>("Errore", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
