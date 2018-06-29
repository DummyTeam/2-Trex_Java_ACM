class Ground extends GameObject {

    Ground() {
        super(DataProvider.getGroundImg());
        setLocation(0, DataProvider.getWindowHeight() - getHeight()-50);
    }




}
