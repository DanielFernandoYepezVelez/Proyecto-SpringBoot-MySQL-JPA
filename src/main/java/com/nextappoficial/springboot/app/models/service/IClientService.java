package com.nextappoficial.springboot.app.models.service;

import java.util.List;

import com.nextappoficial.springboot.app.models.entity.Client;

public interface IClientService {
	
	public List<Client> findAll();
	public void save(Client client);
	public Client findOne(Long id);
	public void delete(Long id);

}
