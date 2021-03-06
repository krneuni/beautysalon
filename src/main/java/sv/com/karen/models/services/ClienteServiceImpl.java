package sv.com.karen.models.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sv.com.karen.models.dao.IClienteDAO;
import sv.com.karen.models.entities.Cliente;
@Service
public class ClienteServiceImpl implements IClienteService{
	@Autowired
	private IClienteDAO clienteDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll(){
		return (List<Cliente>) clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}
}
