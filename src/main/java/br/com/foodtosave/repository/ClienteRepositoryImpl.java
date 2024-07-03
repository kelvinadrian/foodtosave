package br.com.foodtosave.repository;

import br.com.foodtosave.adapter.interfaces.datasource.ClienteRepository;
import br.com.foodtosave.core.entity.Cliente;
import br.com.foodtosave.core.entity.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cliente create(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, email, logradouro, bairro, cep, cidade, uf, complemento, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        Cliente finalCliente = cliente;
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, finalCliente.getNome());
            ps.setString(2, finalCliente.getEmail());
            ps.setString(3, finalCliente.getEndereco().getLogradouro());
            ps.setString(4, finalCliente.getEndereco().getBairro());
            ps.setString(5, finalCliente.getEndereco().getCep());
            ps.setString(6, finalCliente.getEndereco().getCidade());
            ps.setString(7, finalCliente.getEndereco().getUf());
            ps.setString(8, finalCliente.getEndereco().getComplemento());
            ps.setString(9, finalCliente.getEndereco().getNumero());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            return getReferenceById(key.longValue());
        }

        return null;
    }

    @Override
    public Page<Cliente> findAll(Pageable page) {
        String sql = "SELECT * FROM cliente LIMIT ? OFFSET ?";
        List<Cliente> clientes = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            String logradouro = rs.getString("logradouro");
            String bairro = rs.getString("bairro");
            String cep = rs.getString("cep");
            String cidade = rs.getString("cidade");
            String uf = rs.getString("uf");
            String complemento = rs.getString("complemento");
            String numero = rs.getString("numero");

            Endereco endereco = new Endereco(logradouro, bairro, cep, cidade, uf, complemento, numero);
            return new Cliente(id, nome, email, endereco);
        }, page.getPageSize(), page.getOffset());

        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cliente", Integer.class);
        int totalElements = (total != null) ? total : 0;
        return new PageImpl<>(clientes, page, totalElements);
    }


    @Override
    public Cliente getReferenceById(Long id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        List<Cliente> clientes =  jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Long idCliente = rs.getLong("id");
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            String logradouro = rs.getString("logradouro");
            String bairro = rs.getString("bairro");
            String cep = rs.getString("cep");
            String cidade = rs.getString("cidade");
            String uf = rs.getString("uf");
            String complemento = rs.getString("complemento");
            String numero = rs.getString("numero");

            Endereco endereco = new Endereco(logradouro, bairro, cep, cidade, uf, complemento, numero);
            return new Cliente(idCliente, nome, email, endereco);
        });

        if (clientes.isEmpty()) {
            return null; // Ou lançar uma exceção, dependendo do contexto
        } else {
            return clientes.get(0); // Retorna o primeiro cliente encontrado
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), cliente.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
