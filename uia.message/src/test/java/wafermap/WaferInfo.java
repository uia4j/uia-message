package wafermap;

import java.util.ArrayList;

public class WaferInfo {

    private String operationName;

    private String deviceName;

    private int waferSize;

    private int machineNo;

    private int indexSizeX;

    private int indexSizeY;

    private int stdOrientationFlatDirection;

    private int finalEditingMachine;

    private int mapVersion;

    private int mapRowSize;

    private int mapLineSize;

    private int mapDataForm;

    private String waferId;

    private int waferNumberOfProbing;

    private String waferLotNo;

    private int waferCassetteNo;

    private int waferSlotNo;

    private int probingXIncreaseDirection;

    private int probingYIncreaseDirection;

    private int probingDieSetting;

    private int reserved1;

    private int probingTargetDiePosX;

    private int probingTargetDiePosY;

    private int probingReferenceDieCoordX;

    private int probingReferenceDieCoordY;

    private int probingStartPos;

    private int probingDirection;

    private int reserved2;

    private int distanceX2Center;

    private int distanceY2Center;

    private int coordX2Center;

    private int coordY2Center;

    private int dieFirstCoordX;

    private int dieFirstCoordY;

    private String testStartYear;

    private String testStartMonth;

    private String testStartDay;

    private String testStartHour;

    private String testStartMinute;

    private String testStartReserved;

    private String testEndYear;

    private String testEndMonth;

    private String testEndDay;

    private String testEndHour;

    private String testEndMinute;

    private String testEndReserved;

    private String loadingEndYear;

    private String loadingEndMonth;

    private String loadingEndDay;

    private String loadingEndHour;

    private String loadingEndMinute;

    private String loadingEndReserved;

    private String unloadingEndYear;

    private String unloadingEndMonth;

    private String unloadingEndDay;

    private String unloadingEndHour;

    private String unloadingEndMinute;

    private String unloadingEndReserved;

    private int machineNo1;

    private int machineNo2;

    private byte[] special;

    private int testingEndInfo;

    private int testingReserved;

    private int testingTestedDice;

    private int testingPassDice;

    private int testingFailDice;

    private int testingDieInfoAddress;

    private int numberOfLineCategoryData;

    private int lineCategoryAddress;

    private int extMapFileConfig;

    private int extMapMaxMultiSite;

    private int extMapCategories;

    private int extRserved;

    private ArrayList<DieInfo> dies;

    public WaferInfo() {
        this.dies = new ArrayList<DieInfo>();
        this.special = new byte[0];
    }

    public String getOperationName() {
        return this.operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getWaferSize() {
        return this.waferSize;
    }

    public void setWaferSize(int waferSize) {
        this.waferSize = waferSize;
    }

    public int getMachineNo() {
        return this.machineNo;
    }

    public void setMachineNo(int machineNo) {
        this.machineNo = machineNo;
    }

    public int getIndexSizeX() {
        return this.indexSizeX;
    }

    public void setIndexSizeX(int indexSizeX) {
        this.indexSizeX = indexSizeX;
    }

    public int getIndexSizeY() {
        return this.indexSizeY;
    }

    public void setIndexSizeY(int indexSizeY) {
        this.indexSizeY = indexSizeY;
    }

    public int getStdOrientationFlatDirection() {
        return this.stdOrientationFlatDirection;
    }

    public void setStdOrientationFlatDirection(int stdOrientationFlatDirection) {
        this.stdOrientationFlatDirection = stdOrientationFlatDirection;
    }

    public int getFinalEditingMachine() {
        return this.finalEditingMachine;
    }

    public void setFinalEditingMachine(int finalEditingMachine) {
        this.finalEditingMachine = finalEditingMachine;
    }

    public int getMapVersion() {
        return this.mapVersion;
    }

    public void setMapVersion(int mapVersion) {
        this.mapVersion = mapVersion;
    }

    public int getMapRowSize() {
        return this.mapRowSize;
    }

    public void setMapRowSize(int mapRowSize) {
        this.mapRowSize = mapRowSize;
    }

    public int getMapLineSize() {
        return this.mapLineSize;
    }

    public void setMapLineSize(int mapLineSize) {
        this.mapLineSize = mapLineSize;
    }

    public int getMapDataForm() {
        return this.mapDataForm;
    }

    public void setMapDataForm(int mapDataForm) {
        this.mapDataForm = mapDataForm;
    }

    public String getWaferId() {
        return this.waferId;
    }

    public void setWaferId(String waferId) {
        this.waferId = waferId;
    }

    public int getWaferNumberOfProbing() {
        return this.waferNumberOfProbing;
    }

    public void setWaferNumberOfProbing(int waferNumberOfProbing) {
        this.waferNumberOfProbing = waferNumberOfProbing;
    }

    public String getWaferLotNo() {
        return this.waferLotNo;
    }

    public void setWaferLotNo(String waferLotNo) {
        this.waferLotNo = waferLotNo;
    }

    public int getWaferCassetteNo() {
        return this.waferCassetteNo;
    }

    public void setWaferCassetteNo(int waferCassetteNo) {
        this.waferCassetteNo = waferCassetteNo;
    }

    public int getWaferSlotNo() {
        return this.waferSlotNo;
    }

    public void setWaferSlotNo(int waferSlotNo) {
        this.waferSlotNo = waferSlotNo;
    }

    public int getProbingXIncreaseDirection() {
        return this.probingXIncreaseDirection;
    }

    public void setProbingXIncreaseDirection(int probingXIncreaseDirection) {
        this.probingXIncreaseDirection = probingXIncreaseDirection;
    }

    public int getProbingYIncreaseDirection() {
        return this.probingYIncreaseDirection;
    }

    public void setProbingYIncreaseDirection(int probingYIncreaseDirection) {
        this.probingYIncreaseDirection = probingYIncreaseDirection;
    }

    public int getProbingDieSetting() {
        return this.probingDieSetting;
    }

    public void setProbingDieSetting(int probingDieSetting) {
        this.probingDieSetting = probingDieSetting;
    }

    public int getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(int reserved1) {
        this.reserved1 = reserved1;
    }

    public int getProbingTargetDiePosX() {
        return this.probingTargetDiePosX;
    }

    public void setProbingTargetDiePosX(int probingTargetDiePosX) {
        this.probingTargetDiePosX = probingTargetDiePosX;
    }

    public int getProbingTargetDiePosY() {
        return this.probingTargetDiePosY;
    }

    public void setProbingTargetDiePosY(int probingTargetDiePosY) {
        this.probingTargetDiePosY = probingTargetDiePosY;
    }

    public int getProbingReferenceDieCoordX() {
        return this.probingReferenceDieCoordX;
    }

    public void setProbingReferenceDieCoordX(int probingReferenceDieCoordX) {
        this.probingReferenceDieCoordX = probingReferenceDieCoordX;
    }

    public int getProbingReferenceDieCoordY() {
        return this.probingReferenceDieCoordY;
    }

    public void setProbingReferenceDieCoordY(int probingReferenceDieCoordY) {
        this.probingReferenceDieCoordY = probingReferenceDieCoordY;
    }

    public int getProbingStartPos() {
        return this.probingStartPos;
    }

    public void setProbingStartPos(int probingStartPos) {
        this.probingStartPos = probingStartPos;
    }

    public int getProbingDirection() {
        return this.probingDirection;
    }

    public void setProbingDirection(int probingDirection) {
        this.probingDirection = probingDirection;
    }

    public int getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(int reserved2) {
        this.reserved2 = reserved2;
    }

    public int getDistanceX2Center() {
        return this.distanceX2Center;
    }

    public void setDistanceX2Center(int distanceX2Center) {
        this.distanceX2Center = distanceX2Center;
    }

    public int getDistanceY2Center() {
        return this.distanceY2Center;
    }

    public void setDistanceY2Center(int distanceY2Center) {
        this.distanceY2Center = distanceY2Center;
    }

    public int getCoordX2Center() {
        return this.coordX2Center;
    }

    public void setCoordX2Center(int coordX2Center) {
        this.coordX2Center = coordX2Center;
    }

    public int getCoordY2Center() {
        return this.coordY2Center;
    }

    public void setCoordY2Center(int coordY2Center) {
        this.coordY2Center = coordY2Center;
    }

    public int getDieFirstCoordX() {
        return this.dieFirstCoordX;
    }

    public void setDieFirstCoordX(int dieFirstCoordX) {
        this.dieFirstCoordX = dieFirstCoordX;
    }

    public int getDieFirstCoordY() {
        return this.dieFirstCoordY;
    }

    public void setDieFirstCoordY(int dieFirstCoordY) {
        this.dieFirstCoordY = dieFirstCoordY;
    }

    public String getTestStartYear() {
        return this.testStartYear;
    }

    public void setTestStartYear(String testStartYear) {
        this.testStartYear = testStartYear;
    }

    public String getTestStartMonth() {
        return this.testStartMonth;
    }

    public void setTestStartMonth(String testStartMonth) {
        this.testStartMonth = testStartMonth;
    }

    public String getTestStartDay() {
        return this.testStartDay;
    }

    public void setTestStartDay(String testStartDay) {
        this.testStartDay = testStartDay;
    }

    public String getTestStartHour() {
        return this.testStartHour;
    }

    public void setTestStartHour(String testStartHour) {
        this.testStartHour = testStartHour;
    }

    public String getTestStartMinute() {
        return this.testStartMinute;
    }

    public void setTestStartMinute(String testStartMinute) {
        this.testStartMinute = testStartMinute;
    }

    public String getTestStartReserved() {
        return this.testStartReserved;
    }

    public void setTestStartReserved(String testStartReserved) {
        this.testStartReserved = testStartReserved;
    }

    public String getTestEndYear() {
        return this.testEndYear;
    }

    public void setTestEndYear(String testEndYear) {
        this.testEndYear = testEndYear;
    }

    public String getTestEndMonth() {
        return this.testEndMonth;
    }

    public void setTestEndMonth(String testEndMonth) {
        this.testEndMonth = testEndMonth;
    }

    public String getTestEndDay() {
        return this.testEndDay;
    }

    public void setTestEndDay(String testEndDay) {
        this.testEndDay = testEndDay;
    }

    public String getTestEndHour() {
        return this.testEndHour;
    }

    public void setTestEndHour(String testEndHour) {
        this.testEndHour = testEndHour;
    }

    public String getTestEndMinute() {
        return this.testEndMinute;
    }

    public void setTestEndMinute(String testEndMinute) {
        this.testEndMinute = testEndMinute;
    }

    public String getTestEndReserved() {
        return this.testEndReserved;
    }

    public void setTestEndReserved(String testEndReserved) {
        this.testEndReserved = testEndReserved;
    }

    public String getLoadingEndYear() {
        return this.loadingEndYear;
    }

    public void setLoadingEndYear(String loadingEndYear) {
        this.loadingEndYear = loadingEndYear;
    }

    public String getLoadingEndMonth() {
        return this.loadingEndMonth;
    }

    public void setLoadingEndMonth(String loadingEndMonth) {
        this.loadingEndMonth = loadingEndMonth;
    }

    public String getLoadingEndDay() {
        return this.loadingEndDay;
    }

    public void setLoadingEndDay(String loadingEndDay) {
        this.loadingEndDay = loadingEndDay;
    }

    public String getLoadingEndHour() {
        return this.loadingEndHour;
    }

    public void setLoadingEndHour(String loadingEndHour) {
        this.loadingEndHour = loadingEndHour;
    }

    public String getLoadingEndMinute() {
        return this.loadingEndMinute;
    }

    public void setLoadingEndMinute(String loadingEndMinute) {
        this.loadingEndMinute = loadingEndMinute;
    }

    public String getLoadingEndReserved() {
        return this.loadingEndReserved;
    }

    public void setLoadingEndReserved(String loadingEndReserved) {
        this.loadingEndReserved = loadingEndReserved;
    }

    public String getUnloadingEndYear() {
        return this.unloadingEndYear;
    }

    public void setUnloadingEndYear(String unloadingEndYear) {
        this.unloadingEndYear = unloadingEndYear;
    }

    public String getUnloadingEndMonth() {
        return this.unloadingEndMonth;
    }

    public void setUnloadingEndMonth(String unloadingEndMonth) {
        this.unloadingEndMonth = unloadingEndMonth;
    }

    public String getUnloadingEndDay() {
        return this.unloadingEndDay;
    }

    public void setUnloadingEndDay(String unloadingEndDay) {
        this.unloadingEndDay = unloadingEndDay;
    }

    public String getUnloadingEndHour() {
        return this.unloadingEndHour;
    }

    public void setUnloadingEndHour(String unloadingEndHour) {
        this.unloadingEndHour = unloadingEndHour;
    }

    public String getUnloadingEndMinute() {
        return this.unloadingEndMinute;
    }

    public void setUnloadingEndMinute(String unloadingEndMinute) {
        this.unloadingEndMinute = unloadingEndMinute;
    }

    public String getUnloadingEndReserved() {
        return this.unloadingEndReserved;
    }

    public void setUnloadingEndReserved(String unloadingEndReserved) {
        this.unloadingEndReserved = unloadingEndReserved;
    }

    public int getMachineNo1() {
        return this.machineNo1;
    }

    public void setMachineNo1(int machineNo1) {
        this.machineNo1 = machineNo1;
    }

    public int getMachineNo2() {
        return this.machineNo2;
    }

    public void setMachineNo2(int machineNo2) {
        this.machineNo2 = machineNo2;
    }

    public byte[] getSpecial() {
        return this.special;
    }

    public void setSpecial(byte[] special) {
        this.special = special;
    }

    public int getTestingEndInfo() {
        return this.testingEndInfo;
    }

    public void setTestingEndInfo(int testingEndInfo) {
        this.testingEndInfo = testingEndInfo;
    }

    public int getTestingReserved() {
        return this.testingReserved;
    }

    public void setTestingReserved(int testingReserved) {
        this.testingReserved = testingReserved;
    }

    public int getTestingTestedDice() {
        return this.testingTestedDice;
    }

    public void setTestingTestedDice(int testingTestedDice) {
        this.testingTestedDice = testingTestedDice;
    }

    public int getTestingPassDice() {
        return this.testingPassDice;
    }

    public void setTestingPassDice(int testingPassDice) {
        this.testingPassDice = testingPassDice;
    }

    public int getTestingFailDice() {
        return this.testingFailDice;
    }

    public void setTestingFailDice(int testingFailDice) {
        this.testingFailDice = testingFailDice;
    }

    public int getTestingDieInfoAddress() {
        return this.testingDieInfoAddress;
    }

    public void setTestingDieInfoAddress(int testingDieInfoAddress) {
        this.testingDieInfoAddress = testingDieInfoAddress;
    }

    public int getNumberOfLineCategoryData() {
        return this.numberOfLineCategoryData;
    }

    public void setNumberOfLineCategoryData(int numberOfLineCategoryData) {
        this.numberOfLineCategoryData = numberOfLineCategoryData;
    }

    public int getLineCategoryAddress() {
        return this.lineCategoryAddress;
    }

    public void setLineCategoryAddress(int lineCategoryAddress) {
        this.lineCategoryAddress = lineCategoryAddress;
    }

    public int getExtMapFileConfig() {
        return this.extMapFileConfig;
    }

    public void setExtMapFileConfig(int extMapFileConfig) {
        this.extMapFileConfig = extMapFileConfig;
    }

    public int getExtMapMaxMultiSite() {
        return this.extMapMaxMultiSite;
    }

    public void setExtMapMaxMultiSite(int extMapMaxMultiSite) {
        this.extMapMaxMultiSite = extMapMaxMultiSite;
    }

    public int getExtMapCategories() {
        return this.extMapCategories;
    }

    public void setExtMapCategories(int extMapCategories) {
        this.extMapCategories = extMapCategories;
    }

    public int getExtRserved() {
        return this.extRserved;
    }

    public void setExtRserved(int extRserved) {
        this.extRserved = extRserved;
    }

    public ArrayList<DieInfo> getDies() {
        return this.dies;
    }

    public void setDies(ArrayList<DieInfo> dies) {
        this.dies = dies;
    }

    public void print() {
        int i = 0;
        for (int r = 0; r < this.mapRowSize; r++) {
            for (int l = 0; l < this.mapLineSize; l++) {
                int d = this.dies.get(i++).getTestResult();
                switch (d) {
                    case 1:
                        System.out.print(".");
                        break;
                    case 2:
                        System.out.print("x");
                        break;
                    default:
                        System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%s,%s)", this.waferId, this.mapRowSize, this.mapLineSize);
    }
}