#include <iostream>
#include <list>

struct Point {
	int x;
	int y;
};

class Shape {
public:
	virtual void draw() = 0;
	virtual void translate(int) = 0;
};

class Circle : public Shape {
public:
	virtual void draw() {
		std::cerr <<"in drawCircle\n";
	}
	virtual void translate(int delta) {
		std::cerr <<"in translateCircle\n";
	}
private:
	double radius_;
	struct Point center_;
};

class Square : public Shape {
public:
	virtual void draw() {
		std::cerr <<"in drawSquare\n";
	}
	virtual void translate(int delta) {
		std::cerr <<"in translateSquare\n";
	}
private:
	double side_;
	struct Point center_;
};

class Rhomb : public Shape {
public:
	virtual void draw() {
		std::cerr <<"in drawRhomb\n";
	}
	virtual void translate(int delta) {
		std::cerr <<"in translateRhomb\n";
	}
private:
	double side_;
	struct Point center_;
};

void drawShapes(const std::list<Shape*>& fig) {
	std::list<Shape*>:: const_iterator it;
	for (it=fig.begin(); it!=fig.end(); ++it) {
		(*it)->draw();
	}
}
void moveShapes(const std::list<Shape*>& fig) {
	std::list<Shape*>:: const_iterator it;
	for (it=fig.begin(); it!=fig.end(); ++it) {
		(*it)->translate(10);
	}
}

int main() {
	std::list<Shape*> l = {new Circle, new Square, new Square, new Circle, new Rhomb};
	drawShapes(l);
	moveShapes(l);
	return 0;
}
