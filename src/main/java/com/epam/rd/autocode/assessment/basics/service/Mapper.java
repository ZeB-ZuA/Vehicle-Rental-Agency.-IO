package com.epam.rd.autocode.assessment.basics.service;

import com.epam.rd.autocode.assessment.basics.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Mapper {


    public static Client csvToClient(String[] values) {
        long id = values[0].isEmpty() ? 0 : Long.parseLong(values[0].trim());

        String email = parseStringField(values[1]);
        String password = parseStringField(values[2]);
        String name = parseStringField(values[3]);
        BigDecimal balance = parseBigDecimalField(values[4]);
        String driverLicense = parseStringField(values[5]);

        return new Client(id, email, password, name, balance, driverLicense);
    }

    public static Employee csvToEmployee(String[] values) {
        long id = values[0].isEmpty() ? 0 : Long.parseLong(values[0]);
        String email = unquoteEmpty(values[1]);
        String password = unquoteEmpty(values[2]);
        String name = unquoteEmpty(values[3]);
        String phone = unquoteEmpty(values[4]);
        LocalDate dateOfBirth = values[5].isEmpty() ? null : LocalDate.parse(values[5]);

        return new Employee(id, email, password, name, phone, dateOfBirth);
    }

    public static Vehicle csvToVehicle(String[] values) {
        String idValue = safe(values[0]);
        String make = safe(values[1]);
        String model = safe(values[2]);
        String characteristics = safe(values[3]);
        String yearOfProductionValue = safe(values[4]);
        String odometerValue = safe(values[5]);
        String color = safe(values[6]);
        String licensePlate = safe(values[7]);
        String numberOfSeatsValue = safe(values[8]);
        String priceValue = safe(values[9]);
        String requiredLicenseValue = safe(values[10]);
        String bodyTypeValue = safe(values[11]);

        long id = idValue.isEmpty() ? 0 : Long.parseLong(idValue);
        String makeProcessed = unquoteEmpty(make);
        String modelProcessed = unquoteEmpty(model);
        String characteristicsProcessed = unquoteEmpty(characteristics);
        int yearOfProduction = yearOfProductionValue.isEmpty() ? 0 : Integer.parseInt(yearOfProductionValue);
        long odometer = odometerValue.isEmpty() ? 0 : Long.parseLong(odometerValue);
        String colorProcessed = unquoteEmpty(color);
        String licensePlateProcessed = unquoteEmpty(licensePlate);
        int numberOfSeats = numberOfSeatsValue.isEmpty() ? 0 : Integer.parseInt(numberOfSeatsValue);
        BigDecimal price = priceValue.isEmpty() ? null : new BigDecimal(priceValue);
        char requiredLicense = requiredLicenseValue.isEmpty() ? (char) 0 : requiredLicenseValue.charAt(0);
        BodyType bodyType = bodyTypeValue.isEmpty() ? null : BodyType.valueOf(bodyTypeValue);

        return new Vehicle(id, makeProcessed, modelProcessed, characteristicsProcessed,
                yearOfProduction, odometer, colorProcessed, licensePlateProcessed,
                numberOfSeats, price, requiredLicense, bodyType);
    }

    public static Order csvToOrder(String[] values) {

        long id = Long.parseLong(values[0]);
        long clientId = Long.parseLong(values[1]);
        long employeeId = Long.parseLong(values[2]);
        long vehicleId = Long.parseLong(values[3]);
        LocalDateTime startTime = LocalDateTime.parse(values[4]);
        LocalDateTime endTime = LocalDateTime.parse(values[5]);
        BigDecimal price = new BigDecimal(values[6]);

        return new Order(id, clientId, employeeId, vehicleId, startTime, endTime, price);
    }

    public static String[] orderToCsv(Order order) {
        String[] values = new String[7];

        values[0] = String.valueOf(order.getId());
        values[1] = String.valueOf(order.getClientId());
        values[2] = String.valueOf(order.getEmployeeId());
        values[3] = String.valueOf(order.getVehicleId());
        values[4] = order.getStartTime() != null ? order.getStartTime().toString() : null;  // null literal
        values[5] = order.getEndTime() != null ? order.getEndTime().toString() : null;      // null literal
        values[6] = order.getPrice() != null ? order.getPrice().toString() : null;          // null literal

        return values;
    }



    public static String[] vehicleToCsv(Vehicle vehicle) {
        return new String[]{
                String.valueOf(vehicle.getId()),
                wrapEmpty(vehicle.getMake()),
                wrapEmpty(vehicle.getModel()),
                wrapEmpty(vehicle.getCharacteristics()),
                String.valueOf(vehicle.getYearOfProduction()),
                String.valueOf(vehicle.getOdometer()),
                wrapEmpty(vehicle.getColor()),
                wrapEmpty(vehicle.getLicensePlate()),
                String.valueOf(vehicle.getNumberOfSeats()),
                vehicle.getPrice() == null ? null : vehicle.getPrice().toString(),
                vehicle.getRequiredLicense() == '\u0000' ? "" : String.valueOf(vehicle.getRequiredLicense()), // <- aquí es clave
                vehicle.getBodyType() == null ? null : vehicle.getBodyType().name()
        };
    }


    public static String[] clientToCsv(Client client) {
        String[] values = new String[6];

        values[0] = String.valueOf(client.getId());
        values[1] = client.getEmail() != null && client.getEmail().isEmpty() ? "\"\"" : client.getEmail();
        values[2] = client.getPassword();
        values[3] = client.getName();
        values[4] = client.getBalance() != null ? client.getBalance().toString() : null;  // null seguro
        values[5] = client.getDriverLicense();

        return values;
    }


    public static String[] employeeToCsv(Employee employee) {
        String[] values = new String[6];

        values[0] = String.valueOf(employee.getId());
        values[1] = employee.getEmail() != null && employee.getEmail().isEmpty() ? "\"\"" : employee.getEmail();
        values[2] = employee.getPassword();
        values[3] = employee.getName();
        values[4] = employee.getPhone();
        values[5] = employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : null;

        return values;
    }


    private static String wrapEmpty(String value) {
        if (value == null) return null;
        if (value.isEmpty()) return "\"\""; // Esto cubre el caso del string vacío
        return value;
    }


    private static String parseStringField(String value) {
        if (value.equals("\"\"")) {
            return "";  // quoted empty string → real empty string
        }
        if (value.isEmpty()) {
            return null;  // empty field → null
        }
        return value;  // normal case
    }

    private static BigDecimal parseBigDecimalField(String value) {
        if (value.isEmpty()) {
            return null;  // empty field → null
        }
        return new BigDecimal(value);
    }
    private static String unquoteEmpty(String value) {
        if ("\"\"".equals(value)) {
            return "";
        }
        return value.isEmpty() ? null : value;
    }
    private static String safe(String value) {
        return value == null ? "" : value;
    }

}
