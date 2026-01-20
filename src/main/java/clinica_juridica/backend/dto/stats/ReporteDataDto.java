package clinica_juridica.backend.dto.stats;

import java.util.List;

public class ReporteDataDto {
    private List<String> labels;
    private List<Long> values; // Using Long for counts
    private String datasetLabel;
    private String chartType; // "bar", "pie", "horizontalBar", "line"

    public ReporteDataDto() {
    }

    public ReporteDataDto(List<String> labels, List<Long> values, String datasetLabel, String chartType) {
        this.labels = labels;
        this.values = values;
        this.datasetLabel = datasetLabel;
        this.chartType = chartType;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }

    public String getDatasetLabel() {
        return datasetLabel;
    }

    public void setDatasetLabel(String datasetLabel) {
        this.datasetLabel = datasetLabel;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
}
