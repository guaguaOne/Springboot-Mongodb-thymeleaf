package commands

import com.tumei.io.TcpServer
import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.command.BaseCommand
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.support.DefaultListableBeanFactory

@Usage("Tcp服务器管理")
class server extends BaseCommand {
    @Command
    @Usage("暂停")
    def pause(InvocationContext context) {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.attributes.get("spring.beanfactory");
        TcpServer server = factory.getBean(TcpServer.class);
        if (server != null) {
            return String.format("Find Server: %d", server.getSessionCount());
        }
        return "Tcp 服务不存在"
    }

}