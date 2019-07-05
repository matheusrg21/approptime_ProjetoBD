/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package approptime.util;

import java.util.Date;

/**
 *
 * @author mrg.estagiario
 */
public class ValidacaoDatas {
    
    private Date horaInicio;
    private Date horaTermino;
    private Date dataInicio;
    private Date dataTermino;

    public ValidacaoDatas() {
    }

    public ValidacaoDatas(Date horaInicio, Date horatermino, Date dataInicio, Date dataTermino) {
        this.horaInicio = horaInicio;
        this.horaTermino = horatermino;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(Date horaTermino) {
        this.horaTermino = horaTermino;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    
    
    
}
