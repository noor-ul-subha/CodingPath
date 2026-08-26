package codepath;

import java.util.List;

public class GuidanceEngine {
    private GuideNode root;

    public GuidanceEngine() {
        buildTree();
    }

    public GuideNode getRoot() { return root; }

    private void buildTree() {
        root = new GuideNode("What field interests you the most?");

        GuideNode webNode = new GuideNode("Do you want Frontend, Backend, or Full-stack?");
        webNode.addChild("Frontend", new GuideNode(new Recommendation(
                "Frontend Web Development",
                List.of("HTML", "CSS", "JavaScript", "TypeScript"),
                List.of("Frontend Developer", "UI Developer", "Web Designer"),
                List.of(
                        new PlatformLink("freeCodeCamp - Responsive Web Design", "https://www.freecodecamp.org/learn/2022/responsive-web-design/"),
                        new PlatformLink("MDN Web Docs", "https://developer.mozilla.org/en-US/docs/Web"),
                        new PlatformLink("CodeWithHarry (YouTube) - Web Dev in Hindi/Urdu", "https://www.youtube.com/@CodeWithHarry"),
                        new PlatformLink("Traversy Media (YouTube)", "https://www.youtube.com/@TraversyMedia")
                ))));
        webNode.addChild("Backend", new GuideNode(new Recommendation(
                "Backend Web Development",
                List.of("Java", "Python", "PHP", "SQL"),
                List.of("Backend Developer", "API Developer", "Database Developer"),
                List.of(
                        new PlatformLink("freeCodeCamp - Backend & APIs", "https://www.freecodecamp.org/learn/back-end-development-and-apis/"),
                        new PlatformLink("GeeksforGeeks Backend", "https://www.geeksforgeeks.org/backend-development/"),
                        new PlatformLink("Apna College (YouTube)", "https://www.youtube.com/@ApnaCollegeOfficial")
                ))));
        webNode.addChild("Full-stack", new GuideNode(new Recommendation(
                "Full-stack Web Development",
                List.of("HTML", "CSS", "JavaScript", "Java or Python", "SQL"),
                List.of("Full-stack Developer", "Software Engineer"),
                List.of(
                        new PlatformLink("freeCodeCamp Full Curriculum", "https://www.freecodecamp.org/learn/"),
                        new PlatformLink("The Odin Project", "https://www.theodinproject.com/"),
                        new PlatformLink("Apna College (YouTube)", "https://www.youtube.com/@ApnaCollegeOfficial"),
                        new PlatformLink("CodeWithHarry (YouTube)", "https://www.youtube.com/@CodeWithHarry")
                ))));
        root.addChild("Web Development", webNode);

        GuideNode mobileNode = new GuideNode("Cross-platform apps or Native apps?");
        mobileNode.addChild("Cross-platform", new GuideNode(new Recommendation(
                "Cross-platform Mobile Development",
                List.of("Dart (Flutter)", "JavaScript (React Native)"),
                List.of("Mobile App Developer", "Cross-platform Developer"),
                List.of(
                        new PlatformLink("Official Flutter Docs", "https://docs.flutter.dev/"),
                        new PlatformLink("React Native Docs", "https://reactnative.dev/docs/getting-started"),
                        new PlatformLink("The Net Ninja (YouTube) - Flutter", "https://www.youtube.com/@NetNinja")
                ))));
        mobileNode.addChild("Native", new GuideNode(new Recommendation(
                "Native Mobile Development",
                List.of("Kotlin (Android)", "Swift (iOS)"),
                List.of("Android Developer", "iOS Developer"),
                List.of(
                        new PlatformLink("Android Developers Official", "https://developer.android.com/courses"),
                        new PlatformLink("Swift Official Docs", "https://www.swift.org/documentation/")
                ))));
        root.addChild("Mobile App Development", mobileNode);

        root.addChild("Game Development", new GuideNode(new Recommendation(
                "Game Development",
                List.of("C++", "C#", "Python"),
                List.of("Game Developer", "Game Programmer", "Gameplay Engineer"),
                List.of(
                        new PlatformLink("Unity Learn", "https://learn.unity.com/"),
                        new PlatformLink("Unreal Engine Docs", "https://dev.epicgames.com/documentation/en-us/unreal-engine")
                ))));

        root.addChild("Data Science / AI", new GuideNode(new Recommendation(
                "Data Science and AI",
                List.of("Python", "R", "SQL"),
                List.of("Data Analyst", "Data Scientist", "Machine Learning Engineer"),
                List.of(
                        new PlatformLink("Kaggle Learn", "https://www.kaggle.com/learn"),
                        new PlatformLink("freeCodeCamp Data Analysis", "https://www.freecodecamp.org/learn/data-analysis-with-python/"),
                        new PlatformLink("CodeWithHarry (YouTube) - Python", "https://www.youtube.com/@CodeWithHarry")
                ))));

        root.addChild("Systems / Backend Engineering", new GuideNode(new Recommendation(
                "Systems and Backend Engineering",
                List.of("C", "C++", "Go", "Rust"),
                List.of("Systems Engineer", "Backend Engineer", "DevOps Engineer"),
                List.of(
                        new PlatformLink("Go Official Docs", "https://go.dev/doc/"),
                        new PlatformLink("Rust Official Book", "https://doc.rust-lang.org/book/")
                ))));

        root.addChild("Cybersecurity", new GuideNode(new Recommendation(
                "Cybersecurity",
                List.of("Python", "C", "Bash/Shell scripting"),
                List.of("Security Analyst", "Penetration Tester", "Security Engineer"),
                List.of(
                        new PlatformLink("TryHackMe", "https://tryhackme.com/"),
                        new PlatformLink("OWASP Foundation", "https://owasp.org/")
                ))));

        root.addChild("Data Structures & Algorithms (DSA)", new GuideNode(new Recommendation(
                "Data Structures & Algorithms",
                List.of("Java", "C++", "Python"),
                List.of("Software Engineer", "Competitive Programmer", "SDE (Interview Prep)"),
                List.of(
                        new PlatformLink("GeeksforGeeks DSA", "https://www.geeksforgeeks.org/data-structures/"),
                        new PlatformLink("Apna College (YouTube) - DSA", "https://www.youtube.com/@ApnaCollegeOfficial"),
                        new PlatformLink("CodeWithHarry (YouTube) - DSA", "https://www.youtube.com/@CodeWithHarry"),
                        new PlatformLink("LeetCode (Practice)", "https://leetcode.com/")
                ))));
    }
}