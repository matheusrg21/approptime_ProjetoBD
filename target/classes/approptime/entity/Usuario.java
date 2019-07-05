/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.entity;

import approptime.util.Constantes;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author mrg.estagiario
 */
public class Usuario {

    private Long id;
    private String nome;
    private String login;
    private String password;
    private InputStream image;
    private Role cargo;
    private Team time;
    private Departamento departamento;
    private Empresa empresa;

    public Usuario() {
    }

    public Usuario(String nome, String login, String password) {
        this.nome = nome;
        this.login = login;
        this.password = password;
    }

    public Usuario cloneData(){
        Usuario toReturn = new Usuario();
        toReturn.setId(null);
        toReturn.setLogin(this.login);
        toReturn.setNome(this.nome);
        toReturn.setPassword(this.password);

        return toReturn;
    }

    public static String getCsvHeader(String separador){
        return "id_usuario"         + separador
               + "nome_usuario"     + separador
               + "login_usuario"    + separador
               + "password_usuario";
    }

    public String toCsv(String separador, String delimitador){
        return   delimitador + id         + delimitador + separador
               + delimitador + nome       + delimitador + separador
               + delimitador + login      + delimitador + separador
               + delimitador + password   + delimitador;
    }

    public static Usuario getUsuarioFromCSVLine(String linhaCsv, String delimitador){
        Usuario retorno = new Usuario();

        String[] elementos = linhaCsv.split(delimitador);

        if(elementos.length < Constantes.INDICE_CSV_QTD_CAMPOS){
            throw new IllegalArgumentException("Dados obrigatórios para extração do usuário não fornecidos no csv: " + elementos.length + " campos recebidos");
        }

        if(StringUtils.isEmpty(elementos[Constantes.INDICE_CSV_CAMPO_ID_USUARIO])){
            throw new IllegalArgumentException("Id de usuário não fornecido no csv");
        }
        else if(StringUtils.isNumeric(elementos[Constantes.INDICE_CSV_CAMPO_ID_USUARIO]) == false){
            throw new IllegalArgumentException("Id de usuário é inválido: precisa ser um número inteiro");
        }
        else{
            retorno.setId(Long.valueOf(elementos[Constantes.INDICE_CSV_CAMPO_ID_USUARIO]));
        }

        retorno.setLogin(elementos[Constantes.INDICE_CSV_CAMPO_LOGIN_USUARIO]);
        retorno.setNome(elementos[Constantes.INDICE_CSV_CAMPO_NOME_USUARIO]);
        retorno.setPassword(elementos[Constantes.INDICE_CSV_CAMPO_PASSWORD_USUARIO]);

        return retorno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public Role getCargo() {
        return cargo;
    }

    public void setCargo(Role cargo) {
        this.cargo = cargo;
    }

    public Team getTime() {
        return time;
    }

    public void setTime(Team time) {
        this.time = time;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }



}
