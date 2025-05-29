error id: file:///C:/Users/Usuario/OneDrive/Documentos/CURSOS%20SEXTO%20CICLO/Curso%20integrador%20sistemas%20I%20-%20software/proyecto-pa/panaderialosandes/src/main/java/com/proyecto/panaderialosandes/controllers/UsuarioController.java
file:///C:/Users/Usuario/OneDrive/Documentos/CURSOS%20SEXTO%20CICLO/Curso%20integrador%20sistemas%20I%20-%20software/proyecto-pa/panaderialosandes/src/main/java/com/proyecto/panaderialosandes/controllers/UsuarioController.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[39,1]

error in qdox parser
file content:
```java
offset: 1324
uri: file:///C:/Users/Usuario/OneDrive/Documentos/CURSOS%20SEXTO%20CICLO/Curso%20integrador%20sistemas%20I%20-%20software/proyecto-pa/panaderialosandes/src/main/java/com/proyecto/panaderialosandes/controllers/UsuarioController.java
text:
```scala
package com.proyecto.panaderialosandes.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.panaderialosandes.entidades.Usuarios;
import com.proyecto.panaderialosandes.services.UsuarioService;

@Controller
@RequestMapping("/login")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String mostrarLogin(){
        return "vista/login";
    }

    @PostMapping
    public String procesarLogin(@RequestParam String username, @RequestParam String password, Model model){
        Optional<Usuarios> usuario = usuarioService.buscarPorUsername(username);
        if(usuario.isPresent() && usuario.get().getPassword().equals(password)){
            return "redirect:/dashboard"";
        }else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "vista/login";
        }
    }
}
@@
```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:489)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:587)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:584)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:619)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:617)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1306)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:584)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:902)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:687)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:467)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	java.base/java.lang.Thread.run(Thread.java:840)
```
#### Short summary: 

QDox parse error in file:///C:/Users/Usuario/OneDrive/Documentos/CURSOS%20SEXTO%20CICLO/Curso%20integrador%20sistemas%20I%20-%20software/proyecto-pa/panaderialosandes/src/main/java/com/proyecto/panaderialosandes/controllers/UsuarioController.java