package com.aulamatriz.hospital.hospital.service.impl;

import com.aulamatriz.hospital.hospital.entities.CitasEntity;
import com.aulamatriz.hospital.hospital.repository.CitasRepository;
import com.aulamatriz.hospital.hospital.service.CitasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
@Service
public class CitasServiceImpl implements CitasService {

    private final CitasRepository citasRepository;

    public CitasServiceImpl(CitasRepository citasRepository) {
        this.citasRepository = citasRepository;
    }
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public ResponseEntity<?> crearCita(CitasEntity cita) {
        CitasEntity nuevaCita=this.citasRepository.save(cita);

        LocalDateTime inicio = LocalDateTime.parse(cita.getFecha_cita_inicio(), formatter);
        LocalDateTime fin = LocalDateTime.parse(cita.getFecha_cita_fin(), formatter);

        if (fin.isBefore(inicio) || fin.isEqual(inicio)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fecha de inicio no puede ser mayor a la fecha de fin");
        }

        return ResponseEntity
                .ok(nuevaCita);
    }

    @Override
    public ResponseEntity<List<CitasEntity>> listarCitas() {
        return ResponseEntity
                .ok(this.citasRepository.findAll());
    }

    @Override
    public ResponseEntity<CitasEntity> encontrarCita(int id_cita) {
        Optional<CitasEntity> cita=this.citasRepository.findById(id_cita);
        if(cita.isPresent()){
            return ResponseEntity.ok(cita.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @Override
    public ResponseEntity<?> actualizarCita(int id_cita, CitasEntity cita) {
        Optional <CitasEntity> cita_encontrada =this.citasRepository.findById(id_cita);
        if(cita_encontrada.isPresent()){
            LocalDateTime inicio = LocalDateTime.parse(cita.getFecha_cita_inicio(), formatter);
            LocalDateTime fin = LocalDateTime.parse(cita.getFecha_cita_fin(), formatter);

            if (fin.isBefore(inicio) || fin.isEqual(inicio)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fecha de inicio no puede ser mayor a la fecha de fin");
            }


            CitasEntity citasEntity = cita_encontrada.get();
            citasEntity.setMedico(cita.getMedico());
            citasEntity.setPaciente(cita.getPaciente());
            citasEntity.setFecha_cita_inicio(cita.getFecha_cita_inicio());
            citasEntity.setFecha_cita_fin(cita.getFecha_cita_fin());
            citasEntity.setEstado_cita(cita.getEstado_cita());

            CitasEntity nueva_cita=this.citasRepository.save(citasEntity);

            return ResponseEntity.ok(nueva_cita);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<?> eliminarCita(int id_cita) {
        Optional <CitasEntity> cita_encontrada =this.citasRepository.findById(id_cita);
        if(cita_encontrada.isPresent()){
            this.citasRepository.deleteById(id_cita);
            return  ResponseEntity.ok().body("cita eliminada");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cita no encontrada");
    }
}
