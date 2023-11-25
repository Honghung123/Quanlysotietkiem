package com.earntogether.qlysotietkiem.model;

import lombok.Builder;

@Builder
public record ReportModel(int day, int numOfOpened, int numOfClosed) {}
