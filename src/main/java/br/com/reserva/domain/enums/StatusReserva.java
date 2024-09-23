package br.com.reserva.domain.enums;

public enum StatusReserva {

    ATIVA,
    CANCELADA,
    FINALIZADA;

    public static StatusReserva getValue(String status) {
        for(StatusReserva value : StatusReserva.values()) {
            if (value.toString().equals(status))
                return value;
        }
        return null;
    }

}
