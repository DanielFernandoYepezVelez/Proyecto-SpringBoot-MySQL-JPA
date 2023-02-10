package com.nextappoficial.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nextappoficial.springboot.app.models.dao.IClientDao;
import com.nextappoficial.springboot.app.models.entity.Client;

/* (CAPA DE SERVICIO) O (SERVICE LAYER) DENTRO DEL SERVICE =>
 * Una Clase Service Esta Basado En El Patron De Diseño FACADE (Fachada)
 * Un Único Punto De Acceso A Distintos DAOS O Repositories.
 * */
@Service
public class ClientServiceImpl implements IClientService {
	
	/* Otra Característica, Es Que Los Transaccionals Pasan De La Clase
	 * ClientDaoImpl A La Clase ClientServiceImpl */
	
	/* Aquí Tenemos Un DAO, Pero, Podemos Tener Diferentes DAOS
	 * En Este Único Punto De Acceso */
	@Autowired
	private IClientDao clientDao;

	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Client findOne(Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

}