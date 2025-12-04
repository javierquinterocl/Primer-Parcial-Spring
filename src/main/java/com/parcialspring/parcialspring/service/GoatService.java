package com.parcialspring.parcialspring.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.parcialspring.parcialspring.dto.GoatRequest;
import com.parcialspring.parcialspring.dto.GoatResponse;
import com.parcialspring.parcialspring.model.GoatModel;
import com.parcialspring.parcialspring.repository.GoatRepository;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Data
public class GoatService {

    private final GoatRepository repository;

    public GoatService(GoatRepository repository) {
        this.repository = repository;
    }

    // Crear cabra
    public GoatResponse createGoat(GoatRequest request) {
        GoatModel goat = new GoatModel();
        goat.setGoatId(request.getGoatId());
        goat.setName(request.getName());
        goat.setBreed(request.getBreed());
        // Asignación directa de Strings/Long sin conversiones
        goat.setBirthDate(request.getBirthDate());
        goat.setGender(request.getGender());
        goat.setGoatType(request.getGoatType());
        goat.setWeight(request.getWeight());
        goat.setMilkProduction(request.getMilkProduction());
        goat.setFoodConsumption(request.getFoodConsumption());
        goat.setVaccinationsCount(request.getVaccinationsCount());
        goat.setHeatPeriods(request.getHeatPeriods());
        goat.setOffspringCount(request.getOffspringCount());
        goat.setParentId(request.getParentId());
        goat.setStatus(request.getStatus());
        goat.setNotes(request.getNotes());

        GoatModel saved = repository.save(goat);
        return toResponse(saved);
    }

    // Listar cabras
    public List<GoatResponse> findAllGoats() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    // Buscar por id
    public GoatResponse findGoatById(Long id) {
        GoatModel goat = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cabra no encontrada con id " + id));
        return toResponse(goat);
    }

    // Actualizar
    public GoatResponse updateGoat(Long id, GoatRequest request) {
        GoatModel goat = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cabra no encontrada con id " + id));

        goat.setName(request.getName());
        goat.setBreed(request.getBreed());
        goat.setBirthDate(request.getBirthDate());
        goat.setGender(request.getGender());
        goat.setGoatType(request.getGoatType());
        goat.setWeight(request.getWeight());
        goat.setMilkProduction(request.getMilkProduction());
        goat.setFoodConsumption(request.getFoodConsumption());
        goat.setVaccinationsCount(request.getVaccinationsCount());
        goat.setHeatPeriods(request.getHeatPeriods());
        goat.setOffspringCount(request.getOffspringCount());
        goat.setParentId(request.getParentId());
        goat.setStatus(request.getStatus());
        goat.setNotes(request.getNotes());

        GoatModel updated = repository.save(goat);
        return toResponse(updated);
    }

    // Eliminar
    public void deleteGoatById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cabra no encontrada con id " + id);
        }
        repository.deleteById(id);
    }

    private GoatResponse toResponse(GoatModel g) {
        return new GoatResponse(
                g.getId(),
                g.getGoatId(),
                g.getName(),
                g.getBreed(),
                g.getBirthDate(),
                g.getGender(),
                g.getGoatType(),
                g.getWeight(),
                g.getMilkProduction(),
                g.getFoodConsumption(),
                g.getVaccinationsCount(),
                g.getHeatPeriods(),
                g.getOffspringCount(),
                g.getParentId(),
                g.getStatus(),
                g.getNotes(),
                g.getCreatedAt(),
                g.getUpdatedAt()
        );
    }

    // Exportar a Excel con diseño mejorado
    public byte[] exportToExcel() throws IOException {
        List<GoatModel> goats = repository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Registro de Cabras");

            // ========== ESTILOS ==========

            // Estilo para el título principal
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

            // ========== CREAR ENCABEZADO ==========

            int currentRow = 0;

            // Título principal
            org.apache.poi.ss.usermodel.Row titleRow = sheet.createRow(currentRow++);
            titleRow.setHeightInPoints(30);
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("REPORTE DE REGISTRO DE CABRAS");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 17));

            // Fecha de generación
            org.apache.poi.ss.usermodel.Row dateRow = sheet.createRow(currentRow++);
            dateRow.setHeightInPoints(20);
            org.apache.poi.ss.usermodel.Cell dateCell = dateRow.createCell(0);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");
            dateCell.setCellValue("Generado: " + now.format(formatter));
            dateCell.setCellStyle(subtitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 17));

            // Fila vacía
            currentRow++;

            // ========== ENCABEZADOS DE TABLA ==========

            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(currentRow++);
            headerRow.setHeightInPoints(25);

            String[] columns = {
                "ID", "ID Cabra", "Nombre", "Raza", "Fecha Nacimiento", "Género",
                "Tipo", "Peso (kg)", "Prod. Leche (L)", "Consumo Alimento (kg)",
                "Vacunaciones", "Períodos Celo", "Crías", "ID Padre/Madre",
                "Estado", "Notas", "Fecha Creación", "Última Actualización"
            };

            for (int i = 0; i < columns.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // ========== LLENAR DATOS ==========

            int rowNum = currentRow;
            for (GoatModel goat : goats) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum);
                row.setHeightInPoints(20);
                boolean isAlt = (rowNum - currentRow) % 2 == 1;

                CellStyle currentDataStyle = isAlt ? dataStyleAlt : dataStyle;
                CellStyle currentNumberStyle = isAlt ? numberStyleAlt : numberStyle;

                // Datos de texto
                createStyledCell(row, 0, String.valueOf(goat.getId()), currentNumberStyle);
                createStyledCell(row, 1, goat.getGoatId() != null ? goat.getGoatId() : "", currentDataStyle);
                createStyledCell(row, 2, goat.getName() != null ? goat.getName() : "", currentDataStyle);
                createStyledCell(row, 3, goat.getBreed() != null ? goat.getBreed() : "", currentDataStyle);
                createStyledCell(row, 4, goat.getBirthDate() != null ? goat.getBirthDate() : "", currentDataStyle);
                createStyledCell(row, 5, translateGender(goat.getGender()), currentDataStyle);
                createStyledCell(row, 6, translateGoatType(goat.getGoatType()), currentDataStyle);

                // Datos numéricos
                createStyledCell(row, 7, goat.getWeight() != null ? String.format("%.2f", goat.getWeight()) : "0", currentNumberStyle);
                createStyledCell(row, 8, goat.getMilkProduction() != null ? String.format("%.2f", goat.getMilkProduction()) : "0", currentNumberStyle);
                createStyledCell(row, 9, goat.getFoodConsumption() != null ? String.format("%.2f", goat.getFoodConsumption()) : "0", currentNumberStyle);
                createStyledCell(row, 10, String.valueOf(goat.getVaccinationsCount() != null ? goat.getVaccinationsCount() : 0), currentNumberStyle);
                createStyledCell(row, 11, String.valueOf(goat.getHeatPeriods() != null ? goat.getHeatPeriods() : 0), currentNumberStyle);
                createStyledCell(row, 12, String.valueOf(goat.getOffspringCount() != null ? goat.getOffspringCount() : 0), currentNumberStyle);

                createStyledCell(row, 13, goat.getParentId() != null ? goat.getParentId() : "N/A", currentDataStyle);
                createStyledCell(row, 14, translateStatus(goat.getStatus()), currentDataStyle);
                createStyledCell(row, 15, goat.getNotes() != null ? goat.getNotes() : "", currentDataStyle);
                createStyledCell(row, 16, goat.getCreatedAt() != null ? goat.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "", currentDataStyle);
                createStyledCell(row, 17, goat.getUpdatedAt() != null ? goat.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "", currentDataStyle);

                rowNum++;
            }

            // ========== AJUSTAR ANCHO DE COLUMNAS ==========

            int[] columnWidths = {8, 15, 20, 18, 18, 12, 15, 12, 15, 18, 13, 14, 10, 15, 12, 30, 18, 18};
            for (int i = 0; i < columnWidths.length; i++) {
                sheet.setColumnWidth(i, columnWidths[i] * 256);
            }

            // ========== FOOTER CON TOTALES ==========

            org.apache.poi.ss.usermodel.Row footerRow = sheet.createRow(rowNum + 1);
            footerRow.setHeightInPoints(22);
            org.apache.poi.ss.usermodel.Cell footerCell = footerRow.createCell(0);
            footerCell.setCellValue("Total de registros: " + goats.size());
            CellStyle footerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font footerFont = workbook.createFont();
            footerFont.setBold(true);
            footerFont.setFontHeightInPoints((short) 11);
            footerStyle.setFont(footerFont);
            footerCell.setCellStyle(footerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 5));

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // Exportar a PDF con diseño mejorado
    public byte[] exportToPdf() throws IOException {
        List<GoatModel> goats = repository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Configurar documento en orientación horizontal
            Document document = new Document(PageSize.A4.rotate());
            document.setMargins(20, 20, 40, 40);
            PdfWriter.getInstance(document, out);
            document.open();

            // ========== ENCABEZADO PRINCIPAL ==========

            // Definir color corporativo (gris oscuro similar a #6b7c45)
            Color headerColor = new Color(107, 124, 69); // Color verde oliva
            Color headerTextColor = Color.WHITE;
            Color accentColor = new Color(90, 107, 53); // Versión más oscura

            // Título principal con fondo
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            headerTable.setSpacingAfter(10);

            com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            titleFont.setColor(headerTextColor);

            PdfPCell titleCell = new PdfPCell(new Phrase("REPORTE DE REGISTRO DE CABRAS", titleFont));
            titleCell.setBackgroundColor(headerColor);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setPadding(15);
            titleCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(titleCell);

            document.add(headerTable);

            // ========== INFORMACIÓN DE FECHA Y TOTALES ==========

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingAfter(15);
            infoTable.setWidths(new float[]{1, 1});

            com.lowagie.text.Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            infoFont.setColor(new Color(80, 80, 80));

            com.lowagie.text.Font infoBoldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

            // Fecha de generación
            PdfPCell dateCell = new PdfPCell();
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setPadding(5);
            Phrase datePhrase = new Phrase();
            datePhrase.add(new Chunk("Fecha de generación: ", infoBoldFont));
            datePhrase.add(new Chunk(now.format(formatter), infoFont));
            dateCell.addElement(datePhrase);
            infoTable.addCell(dateCell);

            // Total de registros
            PdfPCell totalCell = new PdfPCell();
            totalCell.setBorder(Rectangle.NO_BORDER);
            totalCell.setPadding(5);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            Phrase totalPhrase = new Phrase();
            totalPhrase.add(new Chunk("Total de registros: ", infoBoldFont));
            totalPhrase.add(new Chunk(String.valueOf(goats.size()), infoFont));
            totalCell.addElement(totalPhrase);
            infoTable.addCell(totalCell);

            document.add(infoTable);

            // ========== TABLA DE DATOS ==========

            // Tabla con 12 columnas principales
            PdfPTable table = new PdfPTable(12);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{0.6f, 1.2f, 1.5f, 1.3f, 1f, 1.2f, 0.9f, 1f, 0.8f, 0.8f, 0.8f, 1f});
            table.setSpacingBefore(5);

            // Encabezados de columnas
            com.lowagie.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            headerFont.setColor(Color.WHITE);

            String[] headers = {
                "ID", "ID Cabra", "Nombre", "Raza", "Género", "Tipo",
                "Peso", "Leche", "Vacunas", "Celo", "Crías", "Estado"
            };

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(accentColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                cell.setBorderColor(Color.WHITE);
                cell.setBorderWidth(1);
                table.addCell(cell);
            }

            // Datos
            com.lowagie.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            com.lowagie.text.Font dataBoldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);

            Color rowColor1 = Color.WHITE;
            Color rowColor2 = new Color(245, 245, 245);

            int rowIndex = 0;
            for (GoatModel goat : goats) {
                Color currentRowColor = rowIndex % 2 == 0 ? rowColor1 : rowColor2;

                // ID
                addDataCell(table, String.valueOf(goat.getId()), dataBoldFont, currentRowColor, Element.ALIGN_CENTER);

                // ID Cabra
                addDataCell(table, goat.getGoatId() != null ? goat.getGoatId() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);

                // Nombre
                addDataCell(table, goat.getName() != null ? goat.getName() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);

                // Raza
                addDataCell(table, goat.getBreed() != null ? goat.getBreed() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);

                // Género
                addDataCell(table, translateGender(goat.getGender()), dataFont, currentRowColor, Element.ALIGN_CENTER);

                // Tipo
                addDataCell(table, translateGoatType(goat.getGoatType()), dataFont, currentRowColor, Element.ALIGN_LEFT);

                // Peso
                addDataCell(table, goat.getWeight() != null ? String.format("%.1f kg", goat.getWeight()) : "0", dataFont, currentRowColor, Element.ALIGN_RIGHT);

                // Producción Leche
                addDataCell(table, goat.getMilkProduction() != null ? String.format("%.1f L", goat.getMilkProduction()) : "0", dataFont, currentRowColor, Element.ALIGN_RIGHT);

                // Vacunaciones
                addDataCell(table, String.valueOf(goat.getVaccinationsCount() != null ? goat.getVaccinationsCount() : 0), dataFont, currentRowColor, Element.ALIGN_CENTER);

                // Períodos Celo
                addDataCell(table, String.valueOf(goat.getHeatPeriods() != null ? goat.getHeatPeriods() : 0), dataFont, currentRowColor, Element.ALIGN_CENTER);

                // Crías
                addDataCell(table, String.valueOf(goat.getOffspringCount() != null ? goat.getOffspringCount() : 0), dataFont, currentRowColor, Element.ALIGN_CENTER);

                // Estado
                String status = translateStatus(goat.getStatus());
                Color statusColor = getStatusColor(goat.getStatus());
                PdfPCell statusCell = new PdfPCell(new Phrase(status, dataFont));
                statusCell.setBackgroundColor(statusColor);
                statusCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                statusCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                statusCell.setPadding(5);
                statusCell.setBorderColor(new Color(220, 220, 220));
                table.addCell(statusCell);

                rowIndex++;
            }

            document.add(table);

            // ========== FOOTER ==========

            document.add(new Paragraph("\n"));

            com.lowagie.text.Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            footerFont.setColor(new Color(128, 128, 128));

            Paragraph footer = new Paragraph("Reporte generado automáticamente por el Sistema de Gestión de Cabras", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            return out.toByteArray();
        }
    }

    // Método helper para crear celdas con estilo en Excel
    private void createStyledCell(org.apache.poi.ss.usermodel.Row row, int column, String value, CellStyle style) {
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    // Método helper para agregar celdas de datos en PDF
    private void addDataCell(PdfPTable table, String value, com.lowagie.text.Font font, Color backgroundColor, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setBackgroundColor(backgroundColor);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderColor(new Color(220, 220, 220));
        table.addCell(cell);
    }

    // Métodos de traducción
    private String translateGender(String gender) {
        if (gender == null) return "";
        return gender.equals("MALE") ? "Macho" : gender.equals("FEMALE") ? "Hembra" : gender;
    }

    private String translateGoatType(String goatType) {
        if (goatType == null) return "";
        switch (goatType) {
            case "LEVANTE": return "Levante";
            case "REPRODUCTORA": return "Reproductora";
            case "LECHERA": return "Lechera";
            case "CRIA": return "Cría";
            default: return goatType;
        }
    }

    private String translateStatus(String status) {
        if (status == null) return "";
        switch (status) {
            case "ACTIVE": return "Activo";
            case "SOLD": return "Vendida";
            case "DECEASED": return "Fallecida";
            case "SACRIFICED": return "Sacrificada";
            default: return status;
        }
    }

    // Método para obtener color según estado
    private Color getStatusColor(String status) {
        if (status == null) return Color.WHITE;
        switch (status) {
            case "ACTIVE": return new Color(220, 252, 231); // Verde claro
            case "SOLD": return new Color(254, 249, 195); // Amarillo claro
            case "DECEASED": return new Color(254, 226, 226); // Rojo claro
            case "SACRIFICED": return new Color(229, 231, 235); // Gris claro
            default: return Color.WHITE;
        }
    }
}
