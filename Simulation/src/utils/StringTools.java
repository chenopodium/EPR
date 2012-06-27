package utils;

import java.util.ArrayList;
import java.util.StringTokenizer;

import tools.exceptions.FatalException;
import tools.exceptions.WarningException;
import tools.log.LogUtil;

public class StringTools {
	private static int WIDTH = 80;

	private static LogUtil log = new LogUtil(StringTools.class);

	public static String addNL(String desc, String nl, int WIDTH) {
		if (desc == null || desc.length() < WIDTH)
			return desc;
		int oldnewlinepos = 0;
		int len = desc.length();
		StringBuffer newdesc = new StringBuffer(len + len / WIDTH + 1);
		int sp = desc.indexOf(" ");
		int oldsp = -1;
		int pos = 0;
		int newlinepos = 0;
		for (; pos < desc.length();) {
			int linelen = sp - oldnewlinepos;			
			if (linelen > WIDTH+5) {
				// ideally, cut at previous space
				newlinepos = oldsp;
				// if no other space before that, cut at next space
				if (newlinepos < oldnewlinepos) newlinepos = sp;
				
				linelen = newlinepos - oldnewlinepos;
				
				if (linelen > 1.5 * WIDTH) {
					// just cut in the middle of the word
					newlinepos = oldnewlinepos + WIDTH;
				}
				newdesc = newdesc.append(desc.substring(oldnewlinepos, newlinepos));
				newdesc = newdesc.append(nl);				
				oldnewlinepos = newlinepos;
				pos = newlinepos + 1;
			}
			else {
				pos = sp + 1;
			}
			oldsp = sp;
			if (sp+1 > desc.length()) break;
			sp = desc.indexOf(" ", pos);
			
			if (sp <0) sp = desc.length()-1;
			
		}
		pos = Math.max(oldnewlinepos, newlinepos);
		while (len - pos > WIDTH) {
			newdesc = newdesc.append(desc.substring(pos, pos + WIDTH));
			newdesc = newdesc.append(nl);
			pos += WIDTH;
		}
		newdesc = newdesc.append(desc.substring(pos, len));
		return newdesc.toString();
	}

	public static String replace(String source, String tag, String with) {
		if (source == null || tag == null || tag.length() == 0 || with == null
				|| tag.equals(with)) {
			return source;
		}
		if (tag.indexOf(with) >= 0) {
			String s = source;
			int tagpos = -1;
			while ((tagpos = s.indexOf(tag)) >= 0) {
				StringBuffer result = new StringBuffer();
				result.append(s.subSequence(0, tagpos));
				result.append(with);
				result.append(s.subSequence(tagpos + tag.length(), s.length()));
				s = result.toString();
			}
			return s;
		}
		StringBuffer result = new StringBuffer();
		int pos = 0;
		while (pos < source.length()) {
			int tagpos = source.indexOf(tag, pos);
			if (tagpos != -1) {
				if (tagpos > pos) {
					result.append(source.substring(pos, tagpos));
				}
				result.append(with);
				pos = tagpos + tag.length();
			} else {
				result.append(source.substring(pos));
				break;
			}
		}
		return result.toString();
	}

	public static String getEnumeration(String[] list) {
		String result = "";
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				String element = list[i];
				result += element;
				if (i + 1 < list.length) {
					result += ", ";
				}

			}
		}
		return result;
	}

	public static ArrayList<String> parseList(String list, String sep) {
		if (list == null)
			return null;

		list = list.trim();
		if (list.startsWith("["))
			list = list.substring(1);
		if (list.endsWith("]"))
			list = list.substring(0, list.length() - 1);
		// out("input: "+list+", sep: "+sep);
		ArrayList<String> items = splitString(list, sep);

		return items;
	}

	public static ArrayList<Double> parseListToDouble(String list, String sep) {
		if (list == null)
			return null;

		list = list.trim();
		if (list.startsWith("["))
			list = list.substring(1);
		if (list.endsWith("]"))
			list = list.substring(0, list.length() - 1);
		// p("input: "+list+", sep: "+sep);
		ArrayList<Double> res = new ArrayList<Double>();
		ArrayList<String> items = splitString(list, sep);
		for (String it : items) {
			res.add(Double.parseDouble(it));
		}
		return res;
	}

	public static ArrayList<Long> parseListToLong(String list, String sep) {
		if (list == null)
			return null;

		list = list.trim();
		if (list.startsWith("["))
			list = list.substring(1);
		if (list.endsWith("]"))
			list = list.substring(0, list.length() - 1);
		// p("input: "+list+", sep: "+sep);
		ArrayList<Long> res = new ArrayList<Long>();

		ArrayList<String> items = splitString(list, sep);
		for (String it : items) {
			res.add(Long.parseLong(it));
		}
		return res;
	}
	public static ArrayList<Integer> parseListtoInt(String list, String sep) {
		return parseListtoInt(list, sep, Integer.MAX_VALUE);
	}
	public static ArrayList<Integer> parseListtoInt(String list, String sep, int max) {
		if (list == null)
			return null;

		list = list.trim();
		if (list.startsWith("["))
			list = list.substring(1);
		if (list.endsWith("]"))
			list = list.substring(0, list.length() - 1);
		// p("input: "+list+", sep: "+sep);
		ArrayList<Integer> res = new ArrayList<Integer>();

		ArrayList<String> items = splitString(list, sep);
		for (String it : items) {
			Integer val = Integer.parseInt(it);
			
			if (val > max) {
				warn("Value too large:"+val+", should be < "+max);
				val = max;
			}
			res.add(val);
		}
		return res;
	}
	public static double[] parseListTodouble(String list, String sep) {
		ArrayList<Double> res = parseListToDouble(list, sep);
		if (res == null)
			return null;
		double[] dd = new double[res.size()];
		for (int i = 0; i < res.size(); i++) {
			dd[i] = res.get(i);
		}
		return dd;
	}

	public static ArrayList<String> splitString(String line, String delim) {
		if (line == null) {
			err("splitString: line is null");
			return null;
		}
		ArrayList<String> res = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(line, delim);

		while (tokenizer.hasMoreElements()) {
			String next = tokenizer.nextToken().trim();
			if (next.startsWith("\"")) {
				next = next.substring(1, next.length());
			}
			if (next.endsWith("\"")) {
				next = next.substring(0, next.length() - 1);
			}
			res.add(next);
			// p("token "+res.size()+" is:"+next);
		}
		return res;
	}

	public static ArrayList<Long> splitStringToLongs(String line, String delim) {
		if (line == null) {
			err("splitString: line is null");
			return null;
		}
		ArrayList<Long> res = new ArrayList<Long>();
		StringTokenizer tokenizer = new StringTokenizer(line, delim);

		while (tokenizer.hasMoreElements()) {
			String next = tokenizer.nextToken().trim();
			if (next.startsWith("\"")) {
				next = next.substring(1, next.length());
			}
			if (next.endsWith("\"")) {
				next = next.substring(0, next.length() - 1);
			}
			res.add(new Long(Long.parseLong(next)));
			// p("token "+res.size()+" is:"+next);
		}
		return res;
	}

	public static String toStringList(ArrayList v) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; v != null && i < v.size(); i++) {
			String s = v.get(i).toString();
			sb = sb.append(s);
			if (i + 1 < v.size())
				sb = sb.append(";");
		}
		return sb.toString();
	}

	public static String toStringList(String[] v) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; v != null && i < v.length; i++) {
			String s = v[i];
			sb = sb.append(s);
			if (i + 1 < v.length)
				sb = sb.append(";");
		}
		return sb.toString();
	}

	public static String[] toString(ArrayList v) {
		if (v == null || v.size() < 1)
			return null;
		String ss[] = new String[v.size()];
		for (int i = 0; v != null && i < v.size(); i++) {
			ss[i] = v.get(i).toString();
		}
		return ss;
	}

	public static ArrayList<String> toArrayList(String[] ss) {
		if (ss == null || ss.length < 1)
			return null;
		ArrayList<String> v = new ArrayList<String>(ss.length);
		for (int i = 0; ss != null && i < ss.length; i++) {
			v.add(ss[i]);
		}
		return v;
	}

	public static String getEnglishEnumeration(ArrayList<String> list) {
		String result = "";
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String element = (String) list.get(i);
				result += element;
				if (i + 2 < list.size()) {
					result += ", ";
				}
				if (i + 2 == list.size()) {
					result += " and ";
				}
			}
		}
		return result;
	}

	public static ArrayList<String> parseList(String list) {
		list = replace(list, " ", ",");
		return parseList(list, ",");
	}

	public static void main(String[] args) {

	}

	protected static void warn(String msg) {
		new WarningException(msg);
	}

	protected static void p(String msg) {
		log.info(msg);
	}

	protected static void err(String msg) {
		new FatalException(msg);
	}

	protected static void fatal(String msg) {
		throw new FatalException(msg);
	}
}