package io.elankath.hellomultiporthttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Entry point class of the {@code hellomultiporthttp} service.
 *
 * @author i034796
 */
public class App {
    public final static int DEFAULT_SERVICE_PORT_BEGIN = 8080;
    public final static int DEFAULT_SERVICE_PORT_STEP = 1;
    public final static int DEFAULT_SERVICE_COUNT = 5;

    /**
     * Takes 3 arguments {@code SERVICE_PORT_BEGIN, SERVICE_PORT_STEP and SERVICE_COUNT}
     * Starts a hello HTTP service on the beginning port, by each step until the service count is reached.
     * <p>
     * if args is not specified, then falls back to using environment variables.
     * If any values are unspecified assumes {@code 8080, 1, 1000} as the default values for the beignning port,
     * the step and service count respectively.
     *
     * @param args cli args which are ignored.
     */
    public static void main(String[] args) throws IOException {
        final int servicePortBegin = determineValue(0, args, "SERVICE_PORT_BEGIN", DEFAULT_SERVICE_PORT_BEGIN);
        final int servicePortStep = determineValue(1, args, "SERVICE_PORT_STEP", DEFAULT_SERVICE_PORT_STEP);
        final int serviceCount = determineValue(2, args, "SERVICE_COUNT", DEFAULT_SERVICE_COUNT);
        System.out.printf("Starting services with servicePortBegin: %d, servicePortStep: %d, serviceCount: %d\n",
                servicePortBegin, servicePortStep, serviceCount);
        final List<HelloService> helloServices = new ArrayList<>();
        for (int p = servicePortBegin; p < servicePortBegin + serviceCount; ++p) {
            final HelloService helloService = new HelloService(p);
            helloService.start();
            helloServices.add(helloService);
        }
        System.out.println("STARTED " + helloServices.size() + " services!");
    }

    private static int determineValue(final int argNumber, final String[] args, final String envName, final int defaultValue) {
        final String valStr = System.getenv(envName);
        if (args.length > argNumber + 1) {
            return Integer.parseInt(args[argNumber]);
        } else if (!isNull(valStr)) {
            return Integer.parseInt(valStr);
        } else {
            return defaultValue;
        }
    }

}
