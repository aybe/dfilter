package com.falstad.ripple.client;

public class MovingSource extends Source {
    int moveDuration, pauseDuration;
    int startIter;
    int phase;

    MovingSource() {
        handles.add(new DragHandle(this));
        moveDuration = 900;
        pauseDuration = 500;
        startIter = 0;
        phase = 0;
    }

    void run() {
        DragHandle dh1 = handles.get(0);
        DragHandle dh2 = handles.get(1);
        
        double dur = (phase == 0 || phase == 2) ? moveDuration : pauseDuration;
        double step = (sim.iters-startIter)/dur;
        if (step > 1) {
                step = 1;
                startIter = sim.iters;
                phase++;
                if (phase >= 4)
                        phase = 0;
                step = 0;
        }
        if (phase == 1)
                step = 1;
        if (phase == 3)
                step = 0;
        if (phase == 2)
                step = 1-step;

        double v = getValue();
        double nstep = 1-step;
        if (enabled)
        	RippleSim.drawSource((int)(dh1.x*nstep + dh2.x*step),
        						 (int)(dh1.y*nstep + dh2.y*step), v); 
    }

	public EditInfo getEditInfo(int n) {
		if (n == 0)
			return new EditInfo("Move Duration", moveDuration, 0, 1).
            setDimensionless();
		if (n == 1)
			return new EditInfo("Pause Duration", pauseDuration, 0, 1).
					setDimensionless();
		return super.getEditInfo(n-2);
	}
	public void setEditValue(int n, EditInfo ei) {
		if (n == 0)
            moveDuration = (int)ei.value;
		if (n == 1)
            pauseDuration = (int)ei.value;
		super.setEditValue(n-2, ei);
	}
}
