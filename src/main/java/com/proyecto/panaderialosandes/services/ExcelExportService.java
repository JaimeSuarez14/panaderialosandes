package com.proyecto.panaderialosandes.services;
import com.proyecto.panaderialosandes.models.Productos;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExportService {
    public void generarReporteProductos(List<Productos> productos) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Productos");

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Nombre", "Categoría", "Precio", "Cantidad"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        // Llenar datos de productos
        int rowNum = 1;
        for (Productos producto : productos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(producto.getId());
            row.createCell(1).setCellValue(producto.getNombre());
            row.createCell(2).setCellValue(producto.getCategoria().getNombre());
            row.createCell(3).setCellValue(producto.getPrecio());
            row.createCell(4).setCellValue(producto.getCantidad());
        }

        // Escribir el archivo
        try (FileOutputStream fileOut = new FileOutputStream("productos.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}

