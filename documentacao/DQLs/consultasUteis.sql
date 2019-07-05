/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ebenezer.botelho
 * Created: May 8, 2018
 */

/*Jornada diária realizada*/
select DATE(DT_INICIO) as dia,
       time(min(DT_INICIO)) as inicio,
       time(max(DT_TERMINO)) as termino,
       CONCAT(TIMESTAMPDIFF(hour,min(DT_INICIO), max(DT_TERMINO)),"h",TIMESTAMPDIFF(minute,min(DT_INICIO), max(DT_TERMINO))%60,'m') as jornada
  from approptime.tb_atividade a
    inner join approptime.tb_tarefa t on (a.ID_TAREFA=t.ID)
  where 1=1
    and t.ID_USUARIO = :idusuario
  group by DATE(DT_INICIO)
  order by DT_INICIO desc;

/*Dias com horas extras realizadas, baseado em jornada de 6hs*/
select * from (
  select DATE(DT_INICIO) as dia,
         time(min(DT_INICIO)) as inicio,
         time(max(DT_TERMINO)) as termino,
         CONCAT(TIMESTAMPDIFF(hour,min(DT_INICIO), max(DT_TERMINO)),"h",TIMESTAMPDIFF(minute,min(DT_INICIO), max(DT_TERMINO))%60,'m') as jornada,
         TIMESTAMPDIFF(minute,min(DT_INICIO), max(DT_TERMINO))-60*6 as minutos_extras
    from approptime.tb_atividade a
      inner join approptime.tb_tarefa t on (a.ID_TAREFA=t.ID)
    where 1=1
      and t.ID_USUARIO = :idusuario
    group by DATE(DT_INICIO)
    order by DT_INICIO desc
) r where r.minutos_extras > 0;