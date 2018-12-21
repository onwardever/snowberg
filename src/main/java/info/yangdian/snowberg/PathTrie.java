package info.yangdian.snowberg;

import java.util.*;

public class PathTrie<T extends Describable> implements Paths<T>
{
    public static final Paths<Controller> PATHS = new PathTrie<>();

    private static final String separator = "/";

    private TrieNode root = new TrieNode("ROOT");

    @Override
    public void add(String path, T t)
    {
        String[] paths = decodePath(path);

        if(match(paths,0,root)!=null)
        {
            // TODO: 2018/12/21 处理存在匹配路径的情况
            return;
        }

        TrieNode current = root;

        for (String s : paths)
        {
            current = current.addChild(new TrieNode(s, null,current));
        }

        current.value = t;
    }

    @Override
    public T get(String path,Map<String,String> parameters)
    {
        String[] paths = decodePath(path);

        TrieNode found = match(paths, 0, this.root);

        if (found == null)
        {
            return null;
        }
        else
        {
            TrieNode current = found;
            int i = paths.length - 1;

            while (current != root)
            {
                if (current.wildcard)
                {
                    parameters.put(current.name, paths[i]);
                }

                current = current.parent;
                i--;
            }

            return found.value;
        }
    }

    /**
     *     检查是否已存在相同的解析路径，而不进行实际的节点操作。假设实际路
     * 径没有{@code {name}}这种不规则形式，解析实际路径和检查模板路径均可
     * 使用此方法。
     *
     * 待加入路径                                            已加入路径
     *
     *                                                         root
     *                                 match                    ↓
     * snowberg  offset+1  ------------------------------>   snowberg
     *    ↓                            match                    ↓
     *    a      offset+1  ------------------------------>   {name1}
     *    ↓                            match                    ↓
     *   {b}     offset+1  ------------------------------>      b
     *    ↓                            match                    ↓
     *   {c}     offset+1  ------------------------------>   {name2}  (value!=null)
     *    ↓
     *
     * 进行到这里，说明已加入的路径的最后一个节点依然匹配，返回该节点，如上{name2}。
     *
     *
     * @param paths
     * @param offset
     * @param current
     * @return
     */
    private TrieNode match(String[] paths,int offset,TrieNode current)
    {
        if (offset >= paths.length)
            return current.value == null ? null : current;

        TrieNode toAdd = new TrieNode(paths[offset]);

        List<TrieNode> matches = new ArrayList<>();

        for (Map.Entry<String, TrieNode> entry : current.children.entrySet())
        {
            if (match(toAdd, entry.getValue()))
                matches.add(entry.getValue());
        }

        for (TrieNode match : matches)
        {
            TrieNode result = match(paths, offset + 1, match);

            if(result!=null)
                return result;
        }

        return null;
    }

    private boolean match(TrieNode toAdd, TrieNode added)
    {
        if (toAdd.wildcard)
            return true;

        if (added.wildcard || toAdd.key.equals(added.key))
            return true;

        return false;
    }

    private String[] decodePath(String path)
    {
        String[] paths = path.split(separator);

        List<String> list = new ArrayList<>();

        for (String s : paths)
        {
            if(!s.isEmpty())
                list.add(s);
        }

        return list.toArray(new String[0]);
    }

    class TrieNode
    {
        //设置路径的原始值，如/snowberg/{name}中的{name}
        String key;

        //路径对应的对象，若在路径中，则为空
        T value;

        //参数名，如{name}中的name
        String name;

        //该路径是否是通配符
        boolean wildcard;

        TrieNode parent;
        Map<String, TrieNode> children = new LinkedHashMap<>();

        TrieNode(String key, T value, TrieNode parent)
        {
            this.key = key;
            this.value = value;
            this.parent = parent;
            if(key !=null)
                this.wildcard = key.startsWith("{") && key.endsWith("}");
            if(wildcard)
                this.name = key.substring(1, key.length() - 1);
        }

        TrieNode(String key)
        {
            this(key, null, null);
        }

        TrieNode child(String key)
        {
            return this.children.get(key);
        }

        //为了避免遗失子节点的子节点，若发现同名
        //子节点，只更新value；若无同名子节点，
        //则加入新增节点。方法返回查找到的同名子
        //节点，或新增的子节点。
        TrieNode addChild(TrieNode child)
        {
            TrieNode found = child(child.key);

            if (found != null)
            {
                found.value = child.value;
                return found;
            }
            else
            {
                this.children.put(child.key, child);
                return child;
            }
        }

        //返回当前节点到root之间的完整路径
        String fullPath()
        {
            Stack<String> stack = new Stack<>();

            TrieNode current = this;

            while (current != PathTrie.this.root)
            {
                stack.push(current.key);
                current = current.parent;
            }

            StringBuilder sb = new StringBuilder();

            while (!stack.empty())
            {
                sb.append("/").append(stack.pop());
            }

            return sb.toString();
        }
    }
}
