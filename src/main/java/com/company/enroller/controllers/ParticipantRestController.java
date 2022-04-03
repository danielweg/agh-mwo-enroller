package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET) //pobranie danych o wszystkich uzytkowniach
	public ResponseEntity<?> getParticipants() {
		Collection<Participant> participants = participantService.getAll();
		return new ResponseEntity<>(participants, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) //pobranie danych o wybranym uzytkowniku
	public ResponseEntity<?> getParticipant(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) //usuwanie usera
	public ResponseEntity<?> deleteParticipant(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
		}
		participantService.delete(participant);
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST) //dodawanie nowego usera
	public ResponseEntity<?> addParticipant(@RequestBody Participant participant) {
		Participant foundParticipant = participantService.findByLogin(participant.getLogin());
		if (foundParticipant != null) {
			return new ResponseEntity("Unable to create. A participant with login " + participant.getLogin() + " already exist.", HttpStatus.CONFLICT);
		}
		participantService.add(participant);
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT) //edycja istniejacego usera
	public ResponseEntity<?> updateParticipant(@PathVariable("id") String login, @RequestBody Participant participant) {
		Participant foundParticipant = participantService.findByLogin(login);
		if (foundParticipant == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		foundParticipant.setPassword(participant.getPassword());
		participantService.update(foundParticipant);
		return new ResponseEntity<Participant>(foundParticipant, HttpStatus.OK);
	}




}
