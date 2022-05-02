package com.company.enroller.persistence;

import java.util.Collection;
import java.util.Optional;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@SuppressWarnings("unchecked")
@Component("meetingService")
public class MeetingService {
	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query<Meeting> query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Optional<Meeting> findMeetingById(Long id) {
		String hql = "FROM Meeting WHERE id is :id";
		Query<Meeting> meetingQuery = connector.getSession().createQuery(hql).setParameter("id", id);
		return meetingQuery.uniqueResultOptional();
	}
}