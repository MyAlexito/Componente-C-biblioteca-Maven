package com.multipedidos.componentea.service;

import com.multipedidos.componentea.model.*;
import com.multipedidos.componentea.repository.PedidoRepository;
import com.multipedidos.commons.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    public Pedido crearPedido(Pedido pedido) {

        // convertir ProductoEmbeddable → Producto (de commons-library)
        List<Producto> productos = pedido.getProductos().stream()
                .map(p -> new Producto(p.getNombre(), p.getPrecio()))
                .collect(Collectors.toList());

        double total = OperacionesNegocio.calcularTotal(productos); // commons-library

        pedido.setTotal(total);

        return pedidoRepo.save(pedido);
    }

    public Pedido obtenerPedido(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }
}
