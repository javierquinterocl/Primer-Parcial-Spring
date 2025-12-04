package com.parcialspring.parcialspring.service;

import com.parcialspring.parcialspring.dto.SupplierRequest;
import com.parcialspring.parcialspring.dto.SupplierResponse;
import com.parcialspring.parcialspring.model.SupplierModel;
import com.parcialspring.parcialspring.repository.SupplierRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Data
public class SupplierService {

    private final SupplierRepository repository;

    SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    // ---------------- Métodos del servicio ----------------

    // Crear un proveedor
    public SupplierResponse createSupplier(SupplierRequest request) {
        SupplierModel supplier = new SupplierModel();
        supplier.setSupplierId(request.getSupplierId()); // Ejemplo de generar supplierId
        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setCityId(request.getCityId());
        supplier.setStateId(request.getStateId());
        supplier.setCountryId(request.getCountryId());
        supplier.setNit(request.getNit());
        supplier.setAddress(request.getAddress());

        SupplierModel newSupplier = repository.save(supplier);

        return new SupplierResponse(
                newSupplier.getId(),
                newSupplier.getSupplierId(),
                newSupplier.getName(),
                newSupplier.getPhone(),
                newSupplier.getEmail(),
                newSupplier.getCityId(),
                newSupplier.getStateId(),
                newSupplier.getCountryId(),
                newSupplier.getNit(),
                newSupplier.getAddress(),
                newSupplier.getCreatedAt(),
                newSupplier.getUpdatedAt()
        );
    }

    // Listar todos los proveedores
    public List<SupplierResponse> findAllSuppliers() {
        List<SupplierModel> suppliers = repository.findAll();

        return suppliers.stream()
                .map(s -> new SupplierResponse(
                        s.getId(),
                        s.getSupplierId(),
                        s.getName(),
                        s.getPhone(),
                        s.getEmail(),
                        s.getCityId(),
                        s.getStateId(),
                        s.getCountryId(),
                        s.getNit(),
                        s.getAddress(),
                        s.getCreatedAt(),
                        s.getUpdatedAt()
                ))
                .toList();
    }

    // Buscar proveedor por ID
    public SupplierResponse findSupplierById(Long id) {
        SupplierModel supplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id " + id));

        return new SupplierResponse(
                supplier.getId(),
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getCityId(),
                supplier.getStateId(),
                supplier.getCountryId(),
                supplier.getNit(),
                supplier.getAddress(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt()
        );
    }

    // Actualizar proveedor por ID
    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        SupplierModel supplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id " + id));

        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setCityId(request.getCityId());
        supplier.setStateId(request.getStateId());
        supplier.setCountryId(request.getCountryId());
        supplier.setNit(request.getNit());
        supplier.setAddress(request.getAddress());

        SupplierModel updatedSupplier = repository.save(supplier);

        return new SupplierResponse(
                updatedSupplier.getId(),
                updatedSupplier.getSupplierId(),
                updatedSupplier.getName(),
                updatedSupplier.getPhone(),
                updatedSupplier.getEmail(),
                updatedSupplier.getCityId(),
                updatedSupplier.getStateId(),
                updatedSupplier.getCountryId(),
                updatedSupplier.getNit(),
                updatedSupplier.getAddress(),
                updatedSupplier.getCreatedAt(),
                updatedSupplier.getUpdatedAt()
        );
    }

    // Eliminar proveedor por ID
    public void deleteSupplierById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con id " + id);
        }
        repository.deleteById(id);
    }

    // ========== EXPORTACIÓN A EXCEL ==========
    public byte[] exportToExcel() throws IOException {
        List<SupplierModel> suppliers = repository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Registro de Proveedores");

            // ========== ESTILOS ==========
            CellStyle titleStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 18);
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleStyle.setFont(titleFont);
            titleStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setBorderLeft(BorderStyle.THIN);
            titleStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para el subtítulo (fecha)
            CellStyle subtitleStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font subtitleFont = workbook.createFont();
            subtitleFont.setFontHeightInPoints((short) 11);
            subtitleFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());
            subtitleStyle.setFont(subtitleFont);
            subtitleStyle.setAlignment(HorizontalAlignment.CENTER);
            subtitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Estilo para encabezados de tabla
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerStyle.setBorderTop(BorderStyle.MEDIUM);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para celdas de datos (alternado)
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dataStyleAlt = workbook.createCellStyle();
            dataStyleAlt.cloneStyleFrom(dataStyle);
            dataStyleAlt.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            dataStyleAlt.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Estilo para números
            CellStyle numberStyle = workbook.createCellStyle();
            numberStyle.cloneStyleFrom(dataStyle);
            numberStyle.setAlignment(HorizontalAlignment.RIGHT);

            CellStyle numberStyleAlt = workbook.createCellStyle();
            numberStyleAlt.cloneStyleFrom(dataStyleAlt);
            numberStyleAlt.setAlignment(HorizontalAlignment.RIGHT);

            // ========== ENCABEZADO ==========
            int currentRow = 0;

            // Título principal
            org.apache.poi.ss.usermodel.Row titleRow = sheet.createRow(currentRow++);
            titleRow.setHeightInPoints(30);
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("REPORTE DE PROVEEDORES");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

            // Fecha de generación
            org.apache.poi.ss.usermodel.Row dateRow = sheet.createRow(currentRow++);
            dateRow.setHeightInPoints(20);
            org.apache.poi.ss.usermodel.Cell dateCell = dateRow.createCell(0);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");
            dateCell.setCellValue("Generado: " + now.format(formatter));
            dateCell.setCellStyle(subtitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));

            currentRow++; // Fila vacía

            // Fecha de generación
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(currentRow++);
            headerRow.setHeightInPoints(25);

            String[] columns = {
                    "ID", "ID Proveedor", "Nombre", "Teléfono", "Email",
                    "Ciudad", "Estado", "País", "NIT", "Dirección"
            };

            for (int i = 0; i < columns.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // ========== LLENAR DATOS ==========
            int rowNum = currentRow;
            for (SupplierModel supplier : suppliers) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum);
                row.setHeightInPoints(20);
                boolean isAlt = (rowNum - currentRow) % 2 == 1;

                CellStyle currentDataStyle = isAlt ? dataStyleAlt : dataStyle;
                CellStyle currentNumberStyle = isAlt ? numberStyleAlt : numberStyle;

                createStyledCell(row, 0, String.valueOf(supplier.getId()), currentNumberStyle);
                createStyledCell(row, 1, supplier.getSupplierId(), currentDataStyle);
                createStyledCell(row, 2, supplier.getName(), currentDataStyle);
                createStyledCell(row, 3, supplier.getPhone(), currentDataStyle);
                createStyledCell(row, 4, supplier.getEmail(), currentDataStyle);
                createStyledCell(row, 5, supplier.getCityId(), currentDataStyle);
                createStyledCell(row, 6, supplier.getStateId(), currentDataStyle);
                createStyledCell(row, 7, supplier.getCountryId(), currentDataStyle);
                createStyledCell(row, 8, supplier.getNit(), currentDataStyle);
                createStyledCell(row, 9, supplier.getAddress(), currentDataStyle);

                rowNum++;
            }

            // ========== ANCHO COLUMNAS ==========
            int[] columnWidths = {8, 18, 25, 15, 28, 18, 18, 18, 20, 32};
            for (int i = 0; i < columnWidths.length; i++) {
                sheet.setColumnWidth(i, columnWidths[i] * 256);
            }

            // ========== FOOTER ==========
            org.apache.poi.ss.usermodel.Row footerRow = sheet.createRow(rowNum + 1);
            footerRow.setHeightInPoints(22);
            org.apache.poi.ss.usermodel.Cell footerCell = footerRow.createCell(0);
            footerCell.setCellValue("Total de proveedores: " + suppliers.size());
            CellStyle footerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font footerFont = workbook.createFont();
            footerFont.setBold(true);
            footerFont.setFontHeightInPoints((short) 11);
            footerStyle.setFont(footerFont);
            footerCell.setCellStyle(footerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 4));

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // ========== EXPORTACIÓN A PDF ==========
    public byte[] exportToPdf() throws IOException {
        List<SupplierModel> suppliers = repository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate());
            document.setMargins(20, 20, 40, 40);
            PdfWriter.getInstance(document, out);
            document.open();

            // ========== ENCABEZADO ==========
            Color headerColor = new Color(107, 124, 69);
            Color headerTextColor = Color.WHITE;
            Color accentColor = new Color(90, 107, 53);

            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            headerTable.setSpacingAfter(10);

            com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            titleFont.setColor(headerTextColor);

            PdfPCell titleCell = new PdfPCell(new Phrase("REPORTE DE PROVEEDORES", titleFont));
            titleCell.setBackgroundColor(headerColor);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setPadding(15);
            titleCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(titleCell);

            document.add(headerTable);

            // ========== INFO ==========
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingAfter(15);
            infoTable.setWidths(new float[]{1, 1});

            com.lowagie.text.Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            infoFont.setColor(new Color(80, 80, 80));
            com.lowagie.text.Font infoBoldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

            PdfPCell dateCell = new PdfPCell();
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setPadding(5);
            Phrase datePhrase = new Phrase();
            datePhrase.add(new Chunk("Fecha de generación: ", infoBoldFont));
            datePhrase.add(new Chunk(now.format(formatter), infoFont));
            dateCell.addElement(datePhrase);
            infoTable.addCell(dateCell);

            PdfPCell totalCell = new PdfPCell();
            totalCell.setBorder(Rectangle.NO_BORDER);
            totalCell.setPadding(5);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            Phrase totalPhrase = new Phrase();
            totalPhrase.add(new Chunk("Total de proveedores: ", infoBoldFont));
            totalPhrase.add(new Chunk(String.valueOf(suppliers.size()), infoFont));
            totalCell.addElement(totalPhrase);
            infoTable.addCell(totalCell);

            document.add(infoTable);

            // ========== TABLA DATOS ==========
            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);
            table.setSpacingBefore(5);
            float[] columnWidths = {0.8f, 1.4f, 2f, 1.5f, 2.5f, 1.5f, 1.5f, 1.5f, 1.8f, 2.5f};
            table.setWidths(columnWidths);

            com.lowagie.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            headerFont.setColor(Color.WHITE);

            String[] headers = {
                    "ID", "ID Proveedor", "Nombre", "Teléfono", "Email",
                    "Ciudad", "Estado", "País", "NIT", "Dirección"
            };

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(accentColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                cell.setBorderColor(new Color(200, 200, 200));
                cell.setBorderWidth(1);
                table.addCell(cell);
            }

            com.lowagie.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            com.lowagie.text.Font dataBoldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
            Color rowColor1 = Color.WHITE;
            Color rowColor2 = new Color(245, 245, 245);
            int rowIndex = 0;

            for (SupplierModel supplier : suppliers) {
                Color currentRowColor = rowIndex % 2 == 0 ? rowColor1 : rowColor2;

                addDataCell(table, String.valueOf(supplier.getId()), dataBoldFont, currentRowColor, Element.ALIGN_CENTER);
                addDataCell(table, supplier.getSupplierId() != null ? supplier.getSupplierId() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);
                addDataCell(table, supplier.getName() != null ? supplier.getName() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);
                addDataCell(table, supplier.getPhone() != null ? supplier.getPhone() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);
                addDataCell(table, supplier.getEmail() != null ? supplier.getEmail() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);
                addDataCell(table, supplier.getCityId() != null ? supplier.getCityId() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);
                addDataCell(table, supplier.getStateId() != null ? supplier.getStateId() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);
                addDataCell(table, supplier.getCountryId() != null ? supplier.getCountryId() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);
                addDataCell(table, supplier.getNit() != null ? supplier.getNit() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);
                addDataCell(table, supplier.getAddress() != null ? supplier.getAddress() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);

                rowIndex++;
            }

            document.add(table);

            document.add(new Paragraph("\n"));
            com.lowagie.text.Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            footerFont.setColor(new Color(128, 128, 128));
            Paragraph footer = new Paragraph("Reporte generado automáticamente por el Sistema de Gestión de Proveedores", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return out.toByteArray();
        }
    }

    // ========== MÉTODOS AUXILIARES ==========
    private void createStyledCell(org.apache.poi.ss.usermodel.Row row, int column, String value, CellStyle style) {
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(column);
        cell.setCellValue(value != null ? value : "");
        cell.setCellStyle(style);
    }

    private void addDataCell(PdfPTable table, String value, com.lowagie.text.Font font, Color backgroundColor, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setBackgroundColor(backgroundColor);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderColor(new Color(220, 220, 220));
        table.addCell(cell);
    }
}