package trash.oldschool.xml;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import trash.oldschool.util.FileUtil;

public class XmlWriter implements Closeable {

	private List<Object> reference_list;
	private OutputStreamWriter writer;
	private int index;
	private int indentation;

	public XmlWriter(String fileName) throws IOException {
		this(new FileOutputStream(fileName));
	}

	public XmlWriter(OutputStream stream) throws IOException {
		this.reference_list = new ArrayList<>();
		this.index = 0;
		this.indentation = 0;
		this.writer = FileUtil.openWriter(stream);

		this.startOpeningNode(ROOT_TAG);
		this.printAttribute("version", XmlWriter.ROOT_VERSION);
		this.finishOpeningNode();
	}

	public void write(Object obj) throws IOException, XmlReflectionException {
		reference_list.add(obj);
		index = 0;

		while(index < reference_list.size()) {

			Object tmp = reference_list.get(index);
			Class<?> tmpClass = tmp.getClass();

			XmlClass xmlClass = tmpClass.getAnnotation(XmlClass.class);
			if(xmlClass == null) {
				throw new XmlReflectionException("No annotation for class: " + tmpClass.getName());
			}

			String xmlType = xmlClass.value();

			startOpeningNode(xmlType);
			printAttribute("__id", String.valueOf(index));

			Collection<XmlFieldBinding> bindings = XmlBinding.fieldBindingsOf(xmlType);

			if(bindings == null) {
				throw new XmlReflectionException("No bindings for class: " + tmpClass.getName());
			}

			// possible attributes
			for(XmlFieldBinding fb : bindings) {
				if(fb.attribute) {
					writeAttribute(tmp, fb);
				}
			}

			boolean first = true; // if no children tags, we write a self-closing tag

			// possible children
			for(XmlFieldBinding fb : bindings) {
				if(!fb.attribute) {
					if(first) {
						finishOpeningNode();
						first = false;
					}

					writeField(tmp, fb);
				}
			}

			if(first) {
				finishOpeningNodeWithSelfClosure();
			} else {
				closeNode(xmlType);
			}
			index++;
		}
	}

	private void writeAttribute(Object obj, XmlFieldBinding fb) throws IOException, XmlReflectionException {

		Object value;

		try {
			value = fb.getter.invoke(obj);
		} catch(Exception ex) {
			throw new XmlReflectionException(fb.field, ex);
		}

		if(value == null) {
			return;
		}

		printAttribute(fb.field, value.toString());

	} // End of function

	private void writeField(Object obj, XmlFieldBinding fb) throws IOException, XmlReflectionException {

		Object value;

		try {
			value = fb.getter.invoke(obj);
		} catch(Exception ex) {
			throw new XmlReflectionException(fb.field, ex);
		}

		if(value == null) {
			return;
		}

		openNode(fb.field);

		if(value instanceof Collection<?>)
		{
			Collection<?> list = (Collection<?>)value;
			for(Object o : list) {
				writeItem(fb, o);
			}

		} else if(value.getClass().isArray()) {
			int size = Array.getLength(value);
			for(int i = 0; i < size; i++)
			{
				Object o = Array.get(value, i);
				writeItem(fb, o);
			}

		} else {

			writeItem(fb, value);

		}

		closeNode(fb.field);
	} // End of function

	private void writeItem(XmlFieldBinding fb, Object value) throws IOException, XmlReflectionException {
		if(value == null) {
			return;
		}

		Class<?> c = value.getClass();
		if(XmlBinding.isKindOfPrimitive(c)) {
			printKindOfPrimitive(value.toString(), c);
			return;
		}

		String tag;
		XmlClass annotation = c.getAnnotation(XmlClass.class);
		if(annotation != null) {
			tag = annotation.value();
			if(tag == null || tag.length() == 0) {
				throw new XmlReflectionException("Empty annotation of: " + c.getName());
			}
		}  else {
			throw new XmlReflectionException("Don't know how to write: " + c.getName());
		}

		int reference = reference_list.indexOf(value);
		if(reference == -1)
		{
			reference = reference_list.size();
			reference_list.add(value);
		}

		startOpeningNode(tag);
		printAttribute("__ref", String.valueOf(reference));
		finishOpeningNodeWithSelfClosure();
		return;
	}

	private void openNode(String tag) throws IOException {
		startOpeningNode(tag);
		finishOpeningNode();
	}

	private void startOpeningNode(String tag) throws IOException {
		printIndentation();
		writer.write("<" + tag);
	}

	private void printAttribute(String name, String value) throws IOException {
		writer.write(" " + name + "=\"" + StringEscapeUtils.escapeJava(value) + '\"');
	}

	private void finishOpeningNodeWithSelfClosure() throws IOException {
		writer.write("/>\n");
	}

	private void finishOpeningNode() throws IOException {
		writer.write(">\n");
		indentation += 2;
	}

	private void printKindOfPrimitive(String s, Class<?> class1) throws IOException, XmlReflectionException {
		printIndentation();
		String tag = tagForClass(class1);
		writer.write('<' + tag + '>' + StringEscapeUtils.escapeXml11(s) + "</" + tag + ">\n");
	}

	private String tagForClass(Class<?> class1) throws XmlReflectionException {
		if(class1 == String.class) {
			return "Lang.String";
		}

		if(class1 == int.class || class1 == Integer.class) {
			return "Lang.Integer";
		}

		if(class1 == byte.class || class1 == Byte.class) {
			return "Lang.Byte";
		}

		if(class1 == boolean.class || class1 == Boolean.class) {
			return "Lang.Boolean";
		}

		if(class1 == long.class || class1 == Long.class) {
			return "Lang.Long";
		}

		if(class1 == short.class || class1 == Short.class) {
			return "Lang.Short";
		}

		if(class1 == char.class || class1 == Character.class) {
			return "Lang.Character";
		}

		if(class1 == float.class || class1 == Float.class) {
			return "Lang.Float";
		}

		if(class1 == double.class || class1 == Double.class) {
			return "Lang.Double";
		}

		throw new XmlReflectionException("Couldn't identify parameter type: " + class1.getName());
	}

	private void closeNode(String tag) throws IOException {
		indentation -= 2;
		printIndentation();
		writer.write("</" + tag + ">\n");
	}

	private void printIndentation() throws IOException {
		int tmp = indentation;

		while(tmp > SPACES_LENGTH) {
			writer.write(SPACES_LENGTH);
			tmp -= SPACES_LENGTH;
		}

		if(tmp > 0) {
			writer.write(SPACES.substring(0, tmp));
		}
	}

	@Override
	public void close() throws IOException {
		closeNode(ROOT_TAG);
		writer.close();
	}

	public static final String ROOT_TAG = "data";
	public static final String ROOT_VERSION = "01.00.0000";
	private static final String SPACES = "                                        ";
	private static final int SPACES_LENGTH = SPACES.length();

} // End of class