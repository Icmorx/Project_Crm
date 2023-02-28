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

import it.logicainformatica.projectcrm.bean.AnagraficaBean;
import it.logicainformatica.projectcrm.db.MetodiDB;

// classe che al suo interno contine tutti i servizi rest, uno di tipo post e due di tipo get
@RestController
@RequestMapping("/project-crm")
public class ServiziRest {

	MetodiDB dbMetod = new MetodiDB();

	// servizio rest di tipo post che al suo interno richiama i due metodi che
	// inseriscono i dati, uno sul DB e uno in un file di testo presenti nella classe
	// metodi
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void inserisciAnagrafica(@RequestBody AnagraficaBean usrObj) throws IOException {
		dbMetod.inserisciUtente(usrObj);
		dbMetod.writeFile(usrObj);
	}

	// servizio rest di tipo get che al suo interno richiama il metodo che prende i
	// dati presenti sul DB
	@GetMapping("/getDati")
	public List<AnagraficaBean> getDati() {
		List<AnagraficaBean> lista = dbMetod.getDati();
		return lista;
	}

	// servizio rest di tipo get che al suo interno richiama il metodo che legge i
	// dati dal file di testo
	@GetMapping("/leggiDati")
	public ResponseEntity<String> leggiDati() {

		try {
			// creo un oggetto file, specificando il nome e la directory del file
			File file = new File("Project_Crm.txt");

			// creo un oggetto buffered reader per leggere i dati dal file
			BufferedReader reader = new BufferedReader(new FileReader(file));

			// creo un oggetto string builder per salvare i dati letti dal file
			StringBuilder sB = new StringBuilder();
			String line;
			
			// Leggo il file riga per riga e aggiungo ogni riga letta allo StringBuilder
			while ((line = reader.readLine()) != null) {
				sB.append(line);
				sB.append("\n");
			}

			// chiudo il buffered reader
			reader.close();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);
			return new ResponseEntity<String>(sB.toString(), headers, HttpStatus.OK);

		} catch (IOException e) {
			// Gestisco l'eccezione nel caso in cui non sia possibile leggere il file
			e.printStackTrace();
			System.out.println("Errore di tipo IO nel servizio rest leggiDati " + e.getMessage());
		} catch (Exception e) {
			// Gestisco eventuali eccezioni generiche
			e.printStackTrace();
			System.out.println("Erroe nel servizio rest leggiDati" + e.getMessage());
		}
		return new ResponseEntity<String>("Errore", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
