package control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import equipments.Background;
import equipments.KDTree;
import equipments.MObject;
import equipments.Setupable;
import equipments.Sprite;
import stage.Script;
import stage.Script.MGroup;

public class MHandler {
	private static final ArrayList<ArrayList<MObject>> mObjects = new ArrayList<ArrayList<MObject>>();
	private static ArrayList<ArrayList<MObject>> runObjects;
	private static final ArrayList<MGroup> mGroups = new ArrayList<MGroup>();
	private static final ArrayList<Boolean> runnings = new ArrayList<Boolean>();
	private static boolean loaded = false;
	private static Background shownBackground = null;
	private static final int KDTREE_DIVIDED_WIDTH = 17;
	private static final int KDTREE_DIVIDED_HEIGHT = 13;
	private static final int KDTREE_WIDTH = MPainter.WIDTH / KDTREE_DIVIDED_WIDTH + 1;
	private static final int KDTREE_HEIGHT = MPainter.HEIGHT / KDTREE_DIVIDED_HEIGHT + 1;
	private static final ArrayList<ArrayList<ArrayList<Sprite>>> kdTree = new ArrayList<ArrayList<ArrayList<Sprite>>>();
	private static ArrayList<Sprite> kdTreeCurrent = new ArrayList<Sprite>();
	private static ArrayList<Sprite> kdTreeCurrentLatest = new ArrayList<Sprite>();

	public static final ArrayList<Sprite> kdTreeCurrentLatest() {
		return kdTreeCurrentLatest;
	}

	private static ArrayList<Sprite> kdTreePrevious = new ArrayList<Sprite>();

	private static int kdTreeObjectsAmount = 0;

	public static final int kdTreeObjectsAmount() {
		return kdTreeObjectsAmount;
	}

	public static boolean debugMode = true;

	static final void setKDTree() {
		for (int y = 0; y < KDTREE_DIVIDED_HEIGHT; y++)
			for (int x = 0; x < KDTREE_DIVIDED_WIDTH; x++)
				kdTree.add(new ArrayList<ArrayList<Sprite>>());
	}

	static final void setMain() {
		mObjects.add(new ArrayList<MObject>());
		mGroups.add(MGroup.MAIN_BOTTOM);
		runnings.add(false);
		for (ArrayList<ArrayList<Sprite>> k : kdTree)
			k.add(new ArrayList<Sprite>());
		mObjects.add(new ArrayList<MObject>());
		mGroups.add(MGroup.MAIN_TOP);
		runnings.add(false);
		for (ArrayList<ArrayList<Sprite>> k : kdTree)
			k.add(new ArrayList<Sprite>());
	}
	
	MHandler() {
		setKDTree();
		setMain();
	}

	static final void doneLoading() {
		loaded = true;
	}

	public static final MObject add(MObject mObject) {
		if (mObject == null)
			throw new IllegalArgumentException("objects cannot be null");
		if (mObject instanceof Setupable)
			((Setupable) mObject).setup();
		if (loaded)
			throw new IllegalArgumentException("this method must run in load()");
		boolean newSprite = false;
		int index = 0;
		if (!mGroups.contains(mObject.mGroup)) {
			mObjects.add(new ArrayList<MObject>());
			mGroups.add(mObject.mGroup);
			while (index < mGroups.size()) {
				if (mGroups.get(index) == mObject.mGroup) {
					for (ArrayList<ArrayList<Sprite>> k : kdTree)
						k.add(new ArrayList<Sprite>());
					break;
				}
				index++;
			}
			runnings.add(false);
			newSprite = true;
		}
		int groupIndex = 0;
		if (newSprite)
			groupIndex = index;
		else
			while (mGroups.get(groupIndex) != mObject.mGroup)
				groupIndex++;
		mObjects.get(groupIndex).add(mObject);
		return mObject;
	}

	public static final ArrayList<Integer[]> updateKDTree(Sprite sprite) {
		if (sprite == null)
			throw new IllegalArgumentException("sprite cannot be null");
		if (!(sprite instanceof KDTree))
			throw new IllegalArgumentException("sprite need to implements with KDTree");
		int sX = sprite.point().x;
		int sY = sprite.point().y;
		int sWidth = sprite.dimension().width;
		int sHeight = sprite.dimension().height;
		ArrayList<Integer[]> near = new ArrayList<Integer[]>();
		for (int y = 0; y < KDTREE_DIVIDED_HEIGHT; y++)
			if (y * KDTREE_HEIGHT <= sY + sHeight && sY <= (y + 1) * KDTREE_HEIGHT)
				for (int x = 0; x < KDTREE_DIVIDED_WIDTH; x++)
					if (x * KDTREE_WIDTH <= sX + sWidth && sX <= (x + 1) * KDTREE_WIDTH) {
						for (int i = 0; i < mGroups.size(); i++)
							if (mGroups.get(i) == sprite.mGroup)
								if (!kdTree.get(x + KDTREE_DIVIDED_WIDTH * y).get(i).contains(sprite)) {
									near.add(new Integer[] { x, y, i });
									kdTree.get(x + KDTREE_DIVIDED_WIDTH * y).get(i).add(sprite);
								}
					} else if (sX <= (x + 1) * KDTREE_WIDTH)
						for (int i = 0; i < mGroups.size(); i++)
							if (mGroups.get(i) == sprite.mGroup)
								kdTree.get(x + KDTREE_DIVIDED_WIDTH * y).get(i).remove(sprite);
		return near;
	}

	public static final void removeKDTree(Sprite sprite) {
		if (sprite == null)
			throw new IllegalArgumentException("sprite cannot be null");
		for (Integer[] i : sprite.kdTreeNearSprites())
			kdTree.get(i[0] + KDTREE_DIVIDED_WIDTH * i[1]).get(i[2]).remove(sprite);
	}

	public static final ArrayList<Sprite> kdTreeNearSprite(Sprite sprite) {
		if (sprite == null)
			throw new IllegalArgumentException("sprite cannot be null");
		ArrayList<Sprite> kdTreeNearSprites = new ArrayList<Sprite>();
		kdTreeNearSprites.add(sprite);
		if (sprite.kdTreeNearSprites() != null)
			for (Integer[] i : sprite.kdTreeNearSprites())
				for (Sprite s : kdTree.get(i[0] + KDTREE_DIVIDED_WIDTH * i[1]).get(i[2]))
					if (!kdTreeNearSprites.contains(s))
						kdTreeNearSprites.add(s);
		kdTreeNearSprites.remove(sprite);
		return kdTreeNearSprites;
	}

	private static enum CommandName {
		START, STOP, STOP_ALL
	}

	private static record Command(CommandName commandName, MGroup mGroup) {
	}

	private static final Queue<Command> commands = new LinkedList<Command>();

	static final void loop(Graphics g) {
		while (commands.size() > 0) {
			CommandName commandName = commands.peek().commandName;
			MGroup mGroup = commands.poll().mGroup;
			switch (commandName) {
			case START:
				for (int i = 0; i < mGroups.size(); i++)
					if (mGroups.get(i) == mGroup) {
						runnings.set(i, true);
						break;
					}
				break;
			case STOP:
				for (int i = 0; i < mGroups.size(); i++)
					if (mGroups.get(i) == mGroup) {
						runnings.set(i, false);
						break;
					}
				break;
			case STOP_ALL:
				for (int i = 0; i < runnings.size(); i++)
					runnings.set(i, false);
				break;
			}
		}
		MouseInput.loop();
		KeyInput.loop();
		if (shownBackground != null)
			shownBackground.loop(g);
		else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, MPainter.WIDTH, MPainter.HEIGHT);
		}

		Script.loopBottom(g);
		kdTreePrevious = kdTreeCurrent;
		kdTreeCurrentLatest = kdTreeCurrent;
		kdTreeCurrent = new ArrayList<Sprite>();
		runObjects = mObjects;
		if (runnings.get(0) == true) {
			for (MObject o : runObjects.get(0))
				o.loop(g);
			int x = MouseInfo.getPointerInfo().getLocation().x / KDTREE_WIDTH;
			int y = MouseInfo.getPointerInfo().getLocation().y / KDTREE_HEIGHT;
			for (Sprite s : kdTree.get(x + KDTREE_DIVIDED_WIDTH * y).get(0)) {
				((KDTree) s).kdTreeLoop(g);
				kdTreeCurrent.add(s);
			}
		}
		for (int i = 2; i < runObjects.size(); i++) {
			if (runnings.get(i) == true) {
				for (MObject o : runObjects.get(i))
					o.loop(g);
				int x = MouseInfo.getPointerInfo().getLocation().x / KDTREE_WIDTH;
				int y = MouseInfo.getPointerInfo().getLocation().y / KDTREE_HEIGHT;
				for (Sprite s : kdTree.get(x + KDTREE_DIVIDED_WIDTH * y).get(i)) {
					((KDTree) s).kdTreeLoop(g);
					kdTreeCurrent.add(s);
				}
			}
		}
		if (runnings.get(1) == true) {
			for (MObject o : runObjects.get(1))
				o.loop(g);
			int x = MouseInfo.getPointerInfo().getLocation().x / KDTREE_WIDTH;
			int y = MouseInfo.getPointerInfo().getLocation().y / KDTREE_HEIGHT;
			for (Sprite s : kdTree.get(x + KDTREE_DIVIDED_WIDTH * y).get(1)) {
				((KDTree) s).kdTreeLoop(g);
				kdTreeCurrent.add(s);
			}
		}
		kdTreeObjectsAmount = kdTreeCurrent.size();
		for (MObject o : kdTreeCurrent)
			kdTreePrevious.remove(o);
		for (MObject o : kdTreePrevious)
			((KDTree) o).kdTreeReset();
		Script.loopTop(g);
		if (debugMode) {
			int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			String drawn = "FPS : ";
			if (MPainter.fps() != -1)
				drawn += "" + MPainter.fps();
			else
				drawn += "Loading... ";
			g.drawString(drawn + ", X : " + mouseX + ", Y : " + mouseY, 20, MPainter.HEIGHT - 20);
		}
	}

	public static final void start(MGroup mGroup) {
		if (mGroup == null)
			throw new IllegalArgumentException("mGroup cannot be null");
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		commands.add(new Command(CommandName.START, mGroup));
	}

	public static final void stop(MGroup mGroup) {
		if (mGroup == null)
			throw new IllegalArgumentException("mGroup cannot be null");
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		commands.add(new Command(CommandName.STOP, mGroup));
	}

	public static final void stopAll() {
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		commands.add(new Command(CommandName.STOP_ALL, null));
	}

	public static final void showBackground(Background background) {
		if (background == null)
			throw new IllegalArgumentException("background cannot be null");
		if (!loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		if (background instanceof Setupable)
			((Setupable) background).setup();
		shownBackground = background;
	}

	private static final int getGroupIndex(MObject mObject) {
		if (mObject == null)
			throw new IllegalArgumentException("objects cannot be null");
		int groupIndex = 0;
		while (mGroups.get(groupIndex) != mObject.mGroup)
			groupIndex++;
		return groupIndex;
	}

	public static final ArrayList<MObject> getGroupedObject(MGroup mGroup) {
		if (mGroup == null)
			throw new IllegalArgumentException("groups cannot be null");
		int groupIndex = 0;
		while (mGroups.get(groupIndex) != mGroup)
			groupIndex++;
		return mObjects.get(groupIndex);
	}

	public static final int getIndex(MObject mObject) {
		int objectIndex = 0;
		while (mObjects.get(getGroupIndex(mObject)).get(objectIndex) != mObject)
			objectIndex++;
		return objectIndex;
	}

	public static final boolean moveUp(MObject mObject) {
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		ArrayList<MObject> groupedObjects = mObjects.get(getGroupIndex(mObject));
		int objectIndex = 0;
		while (groupedObjects.get(objectIndex) != mObject)
			objectIndex++;
		if (objectIndex == groupedObjects.size() - 1)
			return false;
		MObject upperObject = groupedObjects.get(objectIndex + 1);
		groupedObjects.set(objectIndex + 1, mObject);
		groupedObjects.set(objectIndex, upperObject);
		return true;
	}

	public static final boolean moveDown(MObject mObject) {
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		ArrayList<MObject> groupedObjects = mObjects.get(getGroupIndex(mObject));
		int objectIndex = 0;
		while (groupedObjects.get(objectIndex) != mObject)
			objectIndex++;
		if (objectIndex == 0)
			return false;
		MObject lowerObject = groupedObjects.get(objectIndex - 1);
		groupedObjects.set(objectIndex - 1, mObject);
		groupedObjects.set(objectIndex, lowerObject);
		return true;
	}

	public static final int moveUp(MObject mObject, int times) {
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		ArrayList<MObject> groupedObjects = mObjects.get(getGroupIndex(mObject));
		int objectIndex = 0;
		while (groupedObjects.get(objectIndex) != mObject)
			objectIndex++;
		int totalTimes = ((groupedObjects.size()) - objectIndex - 1 >= times) ? (times)
				: (groupedObjects.size() - objectIndex - 1);
		times = totalTimes;
		while (objectIndex < groupedObjects.size() - 1 && times > 0) {
			MObject lowerObject = groupedObjects.get(objectIndex + 1);
			groupedObjects.set(objectIndex + 1, mObject);
			groupedObjects.set(objectIndex, lowerObject);
			objectIndex++;
			times--;
		}
		return totalTimes;
	}

	public static final int moveDown(MObject mObject, int times) {
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		ArrayList<MObject> groupedObjects = mObjects.get(getGroupIndex(mObject));
		int objectIndex = 0;
		while (groupedObjects.get(objectIndex) != mObject)
			objectIndex++;
		int totalTimes = (objectIndex >= times) ? (times) : (objectIndex);
		times = totalTimes;
		while (times > 0) {
			MObject lowerObject = groupedObjects.get(objectIndex - 1);
			groupedObjects.set(objectIndex - 1, mObject);
			groupedObjects.set(objectIndex, lowerObject);
			objectIndex--;
			times--;
		}
		return totalTimes;
	}

	public static final int moveTop(MObject mObject) {
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		ArrayList<MObject> groupedObjects = mObjects.get(getGroupIndex(mObject));
		int objectIndex = 0;
		while (groupedObjects.get(objectIndex) != mObject)
			objectIndex++;
		int times = groupedObjects.size() - objectIndex - 1 + 1;
		while (objectIndex < groupedObjects.size() - 1) {
			MObject lowerObject = groupedObjects.get(objectIndex + 1);
			groupedObjects.set(objectIndex + 1, mObject);
			groupedObjects.set(objectIndex, lowerObject);
			objectIndex++;
		}
		return times;
	}

	public static final int moveBottom(MObject mObject) {
		if (loaded)
			throw new IllegalArgumentException("this method must not run in load()");
		ArrayList<MObject> groupedObjects = mObjects.get(getGroupIndex(mObject));
		int objectIndex = 0;
		while (groupedObjects.get(objectIndex) != mObject)
			objectIndex++;
		int times = objectIndex;
		while (objectIndex > 0) {
			MObject lowerObject = groupedObjects.get(objectIndex - 1);
			groupedObjects.set(objectIndex - 1, mObject);
			groupedObjects.set(objectIndex, lowerObject);
			objectIndex--;
		}
		return times;
	}
}
