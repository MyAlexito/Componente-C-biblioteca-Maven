package com.multipedidos.commons;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class OperacionesNegocio {

    public static double calcularTotal(List<Producto> productos) {
        double subtotal = productos.stream().mapToDouble(Producto::getPrecio).sum();
        return calcularTotalConIVA(subtotal);
    }

    public static double calcularTotalConIVA(double subtotal) {
        return Math.round(subtotal * 1.12 * 100.0) / 100.0;
    }

    public static double aplicarDescuento(double total, double porcentaje) {
        return Math.round((total - (total * (porcentaje / 100))) * 100.0) / 100.0;
    }

    public static boolean validarCodigo(String codigo) {
        return codigo != null && codigo.matches("[A-Z]{3}-\\d{4}");
    }

    // Ejemplo de método que consulta un endpoint del Componente A (o B)
    public static String consultarPedidoEnComponenteA(int pedidoId, String baseUrl) throws Exception {
        String url = baseUrl + "/pedidos/" + pedidoId;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() == 200) {
            return resp.body(); // JSON del pedido
        } else {
            throw new RuntimeException("Error al consultar pedido: " + resp.statusCode());
        }
    }
}
